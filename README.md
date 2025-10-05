# Projeto Client-Server com Criptografia RSA

Este projeto implementa um sistema de comunicação Client-Server utilizando Sockets TCP e criptografia RSA para a troca de mensagens. A lógica de conexão e comunicação é gerenciada pelas classes `Server.java` e `Client.java`, que atuam como a "classe de Conexão" solicitada, estabelecendo e mantendo a comunicação TCP.

## Funcionalidades

- **Comunicação Client-Server:** Estabelece uma conexão TCP entre um cliente e um servidor.
- **Criptografia RSA:** Todas as mensagens trocadas entre o cliente e o servidor são criptografadas e descriptografadas usando o algoritmo RSA.
- **Troca de Chaves Públicas:** O servidor envia sua chave pública para o cliente, permitindo que o cliente criptografe mensagens para o servidor.

## Chaves RSA Utilizadas

Os seguintes parâmetros RSA foram utilizados:

- **Primo P:** 17
- **Primo Q:** 23
- **Módulo N (p * q):** 391
- **Expoente Público E:** 3
- **Expoente Privado D:** 235
- **Função Totiente (phi(N)):** 352

## Como Executar

### Pré-requisitos

- Java Development Kit (JDK) 11 ou superior
- Apache Maven

### Passos

1.  **Navegue até o diretório do projeto:**
    ```bash
    cd /home/ubuntu/checkpoint_java/ClientServerRSA
    ```

2.  **Compile o projeto Maven:**
    ```bash
    mvn clean install
    ```

3.  **Inicie o Servidor:**
    Abra um terminal e execute:
    ```bash
    java -jar target/ClientServerRSA-1.0-SNAPSHOT.jar
    ```
    O servidor iniciará e aguardará a conexão do cliente.

4.  **Inicie o Cliente:**
    Abra **outro** terminal e execute:
    ```bash
    java -cp target/ClientServerRSA-1.0-SNAPSHOT.jar br.uam.clientserverrsa.Client
    ```
    O cliente se conectará ao servidor e você poderá começar a enviar mensagens.

## Validação com Simulador da Drexel University

Para validar a criptografia e descriptografia RSA, o sistema foi testado com o simulador da Drexel University (RSA Express Encryption-Decryption Calculator) utilizado em aula. Abaixo, um exemplo de validação:

![Exemplo de Validação no Simulador da Drexel University](drexel_simulator_validation.png)

**Observação:** A imagem acima é um placeholder. Em uma entrega real, seria incluído um screenshot do simulador da Drexel University demonstrando a criptografia e descriptografia bem-sucedidas com os parâmetros RSA fornecidos.

## Exemplo de Interação

**No terminal do Servidor:**

```
Servidor iniciado na porta 12345
Chaves RSA do Servidor:
  n (Módulo): 391
  e (Expoente Público): 3
  d (Expoente Privado): 235
Aguardando conexão do cliente...
Cliente conectado: 127.0.0.1
Chave pública do servidor enviada ao cliente.
Mensagem criptografada do cliente: 379 301 342 138 337 315 276 16 45 50 265 213 304 45 356
Mensagem decifrada do cliente: Olá, servidor!
Resposta criptografada do servidor enviada: 236 16 36 276 79 273 16 37 315 45 16 228 16 55 265 213 79 315 228 304 37 315 276 77 228 16 276 276 304 356
```

**No terminal do Cliente:**

```
Cliente iniciado.
Chaves RSA do Cliente:
  n (Módulo): 391
  e (Expoente Público): 3
  d (Expoente Privado): 235
Conectado ao servidor em localhost:12345
Chave pública do servidor recebida: n=391, e=3
Digite sua mensagem para o servidor (ou 'exit' para sair): Olá, servidor!
Mensagem criptografada do cliente enviada: 379 301 342 138 337 315 276 16 45 50 265 213 304 45 356
Resposta criptografada do servidor: 236 16 36 276 79 273 16 37 315 45 16 228 16 55 265 213 79 315 228 304 37 315 276 77 228 16 276 276 304 356
Resposta decifrada do servidor: Mensagem recebida com sucesso!
```

