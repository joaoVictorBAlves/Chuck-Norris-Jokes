# Piadas do Chuck Norris

> Aluno: João Victor Alves
> 
> 
> **Matrícula:** 509697
> 
> **Disciplina:** Programação para Dispositivos Móveis
> 
> **Professor:** Dr. Windson Viana
> 

Este aplicativo é uma POC (prova de conceito) desenvolvida para o Seminário 02 da disciplina de Programação para Dispositivos Móveis. O tema atribuído foi a biblioteca Retrofit, que facilita a conexão com serviços web. O objetivo foi criar um aplicativo simples para exibir piadas aleatórias do Chuck Norris, consumindo uma API pública por meio do Retrofit.

## Demonstração

| Tema Escuro | Tema Claro |
|------------|-------------|
| ![Tema Claro](https://github.com/joaoVictorBAlves/Chuck-Norris-Jokes/assets/86852231/e8be2839-73a4-4116-90e0-ec28c21f2f5a) | ![Tema Escuro](https://github.com/joaoVictorBAlves/Chuck-Norris-Jokes/assets/86852231/1d3540a5-7d89-4c87-93cb-7b73312d3251) |


## Funcionalidades

- Exibição de piadas aleatórias do Chuck Norris.
- Integração com a API pública utilizando Retrofit.
- Suporte para tema claro e escuro.

## Consumindo a API

Para consumir a API, segui os seguintes passos:

1. **Definição da Interface:**
   Criei uma interface `ChuckNorrisService` para mapear os serviços disponíveis. No caso, como utilizei apenas o método GET para obter as piadas, declarei apenas esse método na interface. Também defini um objeto para mapear o tipo de dado que manipularia o resultado da requisição:

   ```kotlin
   data class ChuckNorrisJoke(
       val id: String,
       val value: String
   )

   interface ChuckNorrisService {
       @GET("jokes/random")
       suspend fun getRandomJoke(): ChuckNorrisJoke
   }
   ```

2. **Configuração do Retrofit:**
   Criei um objeto Retrofit, especificando a URL base da API e o conversor de dados (no caso, utilizei o Gson). Posteriormente, associei o objeto `chuckNorrisService` com base na interface definida anteriormente:

   ```kotlin
   private lateinit var retrofit: Retrofit
   private lateinit var chuckNorrisService: ChuckNorrisService

   // Configuração do Retrofit
   retrofit = Retrofit.Builder()
       .baseUrl("https://api.chucknorris.io/")
       .addConverterFactory(GsonConverterFactory.create())
       .build()

   chuckNorrisService = retrofit.create(ChuckNorrisService::class.java)
   ```

3. **Obtenção das Piadas:**
   Implementei a função `fetchRandomJoke()` para invocar o serviço, usando o método `getRandomJoke()` para realizar a requisição GET e atribuir o resultado ao `TextView` `jokeTxt`:

   ```kotlin
   private fun fetchRandomJoke() {
       CoroutineScope(Dispatchers.IO).launch {
           try {
               val joke = chuckNorrisService.getRandomJoke()
               withContext(Dispatchers.Main) {
                   jokeTxt.text = joke.value
               }
           } catch (e: Exception) {
               // Tratamento de erro
               e.printStackTrace()
           }
       }
   }
   ```
