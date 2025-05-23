# 🚀 BlockBank - Blockchain-based Fintech Application

BlockBank is a blockchain-integrated fintech demo application developed using Java (Spring Boot), Solidity, and Web3j. It enables users to securely perform token swaps on Ethereum, demonstrating realistic blockchain interactions on the Sepolia Testnet.

I built this project as a personal learning initiative while expanding my knowledge and experience in blockchain technology, particularly Solidity smart contract development and backend integration with blockchain services.

## 🌟 Key Features

### 🔒 User Management & Security

* Secure authentication with JWT tokens
* Individual Ethereum wallet generation per user
* AES encryption for safely storing private keys

### 🌐 Blockchain Integration

* Interaction with Ethereum via Web3j
* Using Alchemy to connect to the Sepolia Testnet
* Retrieve ERC-20 token balances (BBUSD, BBETH)

### 📜 Smart Contracts

* Deployed ERC-20 Tokens: BBUSD & BBETH
* Token swap smart contract leveraging Chainlink price feeds
* Solidity best practices: Ownable, Pausable, Non-reentrant

### 🛠 Backend Development

* Developed using Spring Boot 3.4.5 and Java 22
* PostgreSQL database integration
* Modular and maintainable backend architecture

### 📖 API Documentation

* Swagger documentation provided for clear API interaction

## 📌 Deployed Smart Contracts (Sepolia Testnet)

| Contract             | Address                                      |
| -------------------- | -------------------------------------------- |
| BlockbankUSD (BBUSD) | `0x90142A0909F8577799a25e5eaF064eF41e38b298` |
| BlockbankETH (BBETH) | `0x8EB0fcf10A9e134488aC89F0357A63a8501e3895` |
| BlockbankSwap        | `0xEC23441F8db905f7095Bafa42b077C5518254b0a` |

## ⚙️ Installation & Running the Project

### 🐳 Docker Setup (Optional)

Docker setup instructions will be available soon!

### Backend (Spring Boot)

Clone and install dependencies:

```bash
git clone https://github.com/enverkorkmaz/blockbank.git
cd blockbank/backend
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

### Solidity Contracts

The contracts are located in the `contracts/` directory. Compile and deploy them via Remix IDE or Hardhat.

## 🗂 API Documentation

Swagger UI is accessible locally:

```
http://localhost:8080/swagger-ui/index.html
```

## 🚧 Upcoming Features

* Lending & staking modules
* Frontend development (React/Angular integration)
* Monitoring with Prometheus & Grafana

## 📚 Learning & Contributions

Currently, I am actively learning Solidity and aiming to become proficient in blockchain development. Contributions and feedback are always welcome!

---

📫 **Contact**: Feel free to connect or contribute to this project!
