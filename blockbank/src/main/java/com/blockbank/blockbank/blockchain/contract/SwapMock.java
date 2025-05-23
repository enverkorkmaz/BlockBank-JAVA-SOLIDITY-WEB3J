package com.blockbank.blockbank.blockchain.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

@SuppressWarnings("rawtypes")
public class SwapMock extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610429806100206000396000f3fe60806040526004361061001e5760003560e01c806397f74bdf14610023575b600080fd5b61003d60048036038101906100389190610232565b61003f565b005b60003411610082576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161007990610307565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff167f96c68ef9f2be9f6818758d7523c06a96274d4fe5c3cfca55d4c47cb453abb1883484846040516100cc939291906103ae565b60405180910390a25050565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b61013f826100f6565b810181811067ffffffffffffffff8211171561015e5761015d610107565b5b80604052505050565b60006101716100d8565b905061017d8282610136565b919050565b600067ffffffffffffffff82111561019d5761019c610107565b5b6101a6826100f6565b9050602081019050919050565b82818337600083830152505050565b60006101d56101d084610182565b610167565b9050828152602081018484840111156101f1576101f06100f1565b5b6101fc8482856101b3565b509392505050565b600082601f830112610219576102186100ec565b5b81356102298482602086016101c2565b91505092915050565b60008060408385031215610249576102486100e2565b5b600083013567ffffffffffffffff811115610267576102666100e7565b5b61027385828601610204565b925050602083013567ffffffffffffffff811115610294576102936100e7565b5b6102a085828601610204565b9150509250929050565b600082825260208201905092915050565b7f416d6f756e74206d7573742062652067726561746572207468616e207a65726f600082015250565b60006102f16020836102aa565b91506102fc826102bb565b602082019050919050565b60006020820190508181036000830152610320816102e4565b9050919050565b6000819050919050565b61033a81610327565b82525050565b600081519050919050565b60005b8381101561036957808201518184015260208101905061034e565b60008484015250505050565b600061038082610340565b61038a81856102aa565b935061039a81856020860161034b565b6103a3816100f6565b840191505092915050565b60006060820190506103c36000830186610331565b81810360208301526103d58185610375565b905081810360408301526103e98184610375565b905094935050505056fea2646970667358221220a05a8763213b22b24b82385304220e65d49df0365daa7038d6e500b6b43f580064736f6c63430008140033";

    public static final String FUNC_SWAP = "swap";

    public SwapMock(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SwapMock> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SwapMock.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    public static SwapMock load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return load(contractAddress, web3j, new RawTransactionManager(web3j, credentials), contractGasProvider);
    }

    public static SwapMock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SwapMock(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> swap(String fromToken, String toToken, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SWAP,
                Arrays.<Type>asList(new Utf8String(fromToken), new Utf8String(toToken)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }
}
