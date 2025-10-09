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

- Java Development Kit (JDK) 17 ou superior
- Apache Maven
- PostgreSQL database (local or cloud, e.g., Render)

### Configuração do Banco de Dados

1. **Para desenvolvimento local:**
   - Instale PostgreSQL localmente.
   - Crie um banco de dados.
   - Configure as credenciais no arquivo `src/main/resources/application-dev.yml`.
   - Execute a aplicação com perfil dev: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

2. **Para produção (Render):**
   - Crie um banco PostgreSQL no Render.
   - Obtenha a URL externa do banco.
   - Configure as variáveis de ambiente:
     - `SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d3jgpt49c44c73bs8krg-a:5432/clienteserverrsa?sslmode=require`
     - `SPRING_DATASOURCE_USERNAME=clienteserverrsa_user`
     - `SPRING_DATASOURCE_PASSWORD=Wv2NYEdFTGFD08ErP3GVeq4Jf0wW3zst`
     - `PORT=8080` (ou conforme necessário)

### Passos

1.  **Navegue até o diretório do projeto:**
    ```bash
    cd ClientServerRSA
    ```

2.  **Compile e execute a aplicação Spring Boot:**
    ```bash
    mvn spring-boot:run
    ```
    Para desenvolvimento local com perfil dev:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```

3.  **Para executar o cliente/servidor RSA via console (opcional):**
    - Compile: `mvn clean package -DskipTests`
    - Servidor: `java -cp target/classes br.uam.clientserverrsa.Server`
    - Cliente (em outro terminal): `java -cp target/classes br.uam.clientserverrsa.Client`

## Validação com Simulador da Drexel University

Para validar a criptografia e descriptografia RSA, o sistema foi testado com o simulador da Drexel University (RSA Express Encryption-Decryption Calculator) utilizado em aula. Abaixo, um exemplo de validação:

![Exemplo de Validação no Simulador da Drexel University](drexel_simulator_validation.png)


## Exemplo de Interação

**No terminal do Servidor:**


![Exemplo no terminal](docs\image.png)


**No terminal do Cliente:**

![Exemplo no terminal](docs\image2.png)

## Etapas do Fluxo

### 1. Inicialização do Servidor
- Servidor iniciado na porta 12345
- Chaves RSA do Servidor exibidas
- Aguardando conexão do cliente...

### 2. Conexão do Cliente
- Cliente iniciado
- Chaves RSA do Cliente exibidas
- Conectado ao servidor em localhost:12345

### 3. Troca de Chaves Públicas
- Cliente conectado: 127.0.0.1 (no servidor)
- Chave pública do servidor enviada ao cliente (n=391, e=3)
- Chave pública do servidor recebida: n=391, e=3 (no cliente)

### 4. Comunicação Criptografada
- Digite sua mensagem para o servidor (ou 'exit' para sair): [usuário digita mensagem]
- Mensagem criptografada do cliente enviada: [valores criptografados]
- Mensagem criptografada do cliente: [valores] (no servidor)
- Mensagem decifrada do cliente: [mensagem original]
- Resposta criptografada do servidor enviada: [valores]
- Resposta criptografada do servidor: [valores] (no cliente)
- Resposta decifrada do servidor: Mensagem recebida com sucesso!

### Diagrama Simples do Fluxo

```
[Servidor] ---- Inicia na porta 12345 ----> [Aguarda Conexão]
     |
     | <---- Conecta (Cliente) ---- [Cliente] ---- Inicia e conecta
     |
     | ---- Envia chave pública ---->
     | <---- Recebe chave pública ----
     |
     | <---- Envia mensagem criptografada ----
     | ---- Recebe e descriptografa ----
     | ---- Envia resposta criptografada ---->
     | <---- Recebe e descriptografa ----
     |
     | ---- Fecha conexão ----> [Fim]
```

![Exemplo no terminal](docs\create_ferramenta.png)

![Exemplo no terminal](docs\Read_ferramenta.png)

