package com.keyvault.keys.quickstart;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.models.EncryptResult;
import com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm;
import com.azure.security.keyvault.keys.models.DeletedKey;
import com.azure.security.keyvault.keys.models.KeyType;
import com.azure.security.keyvault.keys.models.KeyVaultKey;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String keyVaultName = "kvmsidev02";//System.getenv("KEY_VAULT_NAME");
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
        System.out.println(keyVaultUri);
        /*KeyClient keyClient = new KeyClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        keyClient.createKey("myKey", KeyType.RSA);*/
        CryptographyClient cryptoClient = new CryptographyClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .keyIdentifier("https://kvmsidev02.vault.azure.net/keys/myKey/4065529c75024393b39a798b54255d75")
                .buildClient();

        byte[] plainText = new byte[100];
        new Random(0x1234567L).nextBytes(plainText);

// Let's encrypt a simple plain text of size 100 bytes.
        EncryptResult encryptionResult = cryptoClient.encrypt(EncryptionAlgorithm.RSA_OAEP, plainText);
        System.out.printf("Returned cipherText size is %d bytes with algorithm \"%s\"%n",
                encryptionResult.getCipherText().length, encryptionResult.getAlgorithm());

    }
}
