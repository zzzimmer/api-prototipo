Ta, mas como que ele cria um novo DetalhesEventoDTO se os construtores dessa classe não preveem um Optional<Evento> que o método retorna??

@GetMapping("/eventos/{idEvento}")
public ResponseEntity<DetalhesEventoDTO> eventoEspecifico(@PathVariable Long idEvento) {
return ResponseEntity.of(
eventoRepository.findById(idEvento)
.map(DetalhesEventoDTO::new)
);
}


Certo — direto e técnico. Vou explicar **passo a passo** o que acontece em tempo de compilação e em tempo de execução, com equivalentes explícitos, para você entender por que `map(DetalhesEventoDTO::new)` funciona mesmo sem um construtor que aceite `Optional<Evento>`.



---

### Resumo em 1 linha

`Optional.map` **pega o valor interno** (o `Evento`) e aplica a função que você passou — no caso `DetalhesEventoDTO::new` é uma função `Evento -> DetalhesEventoDTO`. O `Optional` NÃO é passado para o construtor; o valor **interno** é.

---

### Tipos envolvidos (assinaturas)

* `eventoRepository.findById(id)` retorna `Optional<Evento>`.
* `Optional<T>.map(Function<? super T, ? extends U>)` recebe uma `Function<T,U>` e retorna `Optional<U>`.
* `DetalhesEventoDTO::new` (referência de construtor) tem o tipo funcional `Function<Evento, DetalhesEventoDTO>` — ou seja, recebe um `Evento` e retorna um `DetalhesEventoDTO`.
* Portanto: `Optional<Evento>.map(DetalhesEventoDTO::new)` → `Optional<DetalhesEventoDTO>`.

---

### O que o `map` faz, na prática (pseudo-código)

O `map` funciona mais ou menos assim:

```java
// versão simplificada do Optional.map
public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    if (!this.isPresent()) {
        return Optional.empty();
    } else {
        return Optional.of(mapper.apply(this.get()));
    }
}
```

Perceba: `mapper.apply(this.get())` recebe **o objeto T** (`Evento`), não o `Optional`.

---

### Equivalentes concretos — do mais curto ao mais explícito

1. Sua forma (compacta, idiomática):

```java
return ResponseEntity.of(
    eventoRepository.findById(idEvento)
                    .map(DetalhesEventoDTO::new)
);
```

2. Mesmo com lambda (idêntico):

```java
return ResponseEntity.of(
    eventoRepository.findById(idEvento)
                    .map(e -> new DetalhesEventoDTO(e))
);
```

3. Versão equivalente sem `Optional.map` (explicita o fluxo):

```java
Optional<Evento> opt = eventoRepository.findById(idEvento);
if (opt.isPresent()) {
    DetalhesEventoDTO dto = new DetalhesEventoDTO(opt.get());
    return ResponseEntity.ok(dto);
} else {
    return ResponseEntity.notFound().build();
}
```

As três formas fazem exatamente a mesma coisa. `map` só evita o `if/else` manual.

---

### E a chamada `DetalhesEventoDTO::new` — como o compilador resolve isso?

Quando você escreve `DetalhesEventoDTO::new`, o compilador procura um construtor público de `DetalhesEventoDTO` compatível com o tipo de entrada esperado pela `Function` — aqui `Evento`. Você já tem `public DetalhesEventoDTO(Evento evento)` — ótimo, então `DetalhesEventoDTO::new` é equivalente a `(Evento e) -> new DetalhesEventoDTO(e)`.

---

### O que `ResponseEntity.of(Optional<T>)` faz

Assinatura: `public static <T> ResponseEntity<T> of(Optional<T> body)`

Comportamento:

* se `body.isPresent()` → `ResponseEntity.ok(body.get())` (status 200)
* se vazio → `ResponseEntity.notFound().build()` (status 404)

Portanto combinar `findById(...).map(DetalhesEventoDTO::new)` com `ResponseEntity.of(...)` é exatamente o caminho correto e idiomático.

---

### Armadilhas / avisos práticos

* Se `eventoRepository.findById` retornar `Optional.empty()`, **o construtor não é chamado** — sem NPE aí.
* Se dentro do `DetalhesEventoDTO(Evento)` você acessar `evento.getResponsavel().getId()` sem checar `responsavel == null`, aí sim pode dar NPE — trate isso dentro do construtor ou garanta que o Evento sempre tenha responsavel.
* Não confunda `Optional<Evento>` com `Evento` — `map` faz a extração condicional para você.

---

### Conclusão curta

`map(DetalhesEventoDTO::new)` funciona porque `map` extrai o `Evento` do `Optional` e passa esse `Evento` para a função (a referência do construtor). `Optional` não é passado ao construtor — o valor interno é. Use essa forma; é limpa e correta.

Quer que eu escreva uma versão defensiva do construtor `DetalhesEventoDTO` que trata `responsavel == null` pra evitar NPEs?
