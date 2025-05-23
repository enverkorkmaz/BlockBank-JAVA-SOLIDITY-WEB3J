package com.blockbank.blockbank.blockchain.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class BlockbankSwap extends Contract {
    public static final String BINARY = "0x";

    protected BlockbankSwap(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, gasProvider);
    }

    public static BlockbankSwap load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        return new BlockbankSwap(contractAddress, web3j, transactionManager, gasProvider);
    }

    public RemoteFunctionCall<BigInteger> getEstimatedBbethForBbusd(BigInteger bbusdAmount) {
        final Function function = new Function(
                "getEstimatedBbethForBbusd",
                Collections.singletonList(new Uint256(bbusdAmount)),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getEstimatedBbusdForBbeth(BigInteger bbethAmount) {
        final Function function = new Function(
                "getEstimatedBbusdForBbeth",
                Collections.singletonList(new Uint256(bbethAmount)),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> swapBbusdToBbeth(BigInteger bbusdAmount, BigInteger minBbethOut) {
        final Function function = new Function(
                "swapBbusdToBbeth",
                Arrays.asList(new Uint256(bbusdAmount), new Uint256(minBbethOut)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> swapBbethToBbusd(BigInteger bbethAmount, BigInteger minBbusdOut) {
        final Function function = new Function(
                "swapBbethToBbusd",
                Arrays.asList(new Uint256(bbethAmount), new Uint256(minBbusdOut)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositBbeth(BigInteger amount) {
        final Function function = new Function(
                "depositBbeth",
                Collections.singletonList(new Uint256(amount)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositBbusd(BigInteger amount) {
        final Function function = new Function(
                "depositBbusd",
                Collections.singletonList(new Uint256(amount)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pause() {
        final Function function = new Function(
                "pause",
                Collections.emptyList(),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unpause() {
        final Function function = new Function(
                "unpause",
                Collections.emptyList(),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> paused() {
        final Function function = new Function(
                "paused",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Bool>() {})
        );
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(
                "owner",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Address>() {})
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                "transferOwnership",
                Collections.singletonList(new Address(newOwner)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> emergencyWithdraw(String to) {
        final Function function = new Function(
                "emergencyWithdraw",
                Collections.singletonList(new Address(to)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
}
