
package com.sshtools.server.components;

import java.io.IOException;
import java.math.BigInteger;

import com.sshtools.common.ssh.SecurityLevel;
import com.sshtools.common.ssh.SshException;
import com.sshtools.common.ssh.components.SshPrivateKey;
import com.sshtools.common.ssh.components.SshPublicKey;
import com.sshtools.server.SshServerContext;
import com.sshtools.synergy.ssh.SshTransport;
import com.sshtools.synergy.ssh.components.SshKeyExchange;

/**
 * 
 * <p>
 * Abstract representation of an SSH key exchange.
 * </p>
 * 
 * 
 */
public abstract class SshKeyExchangeServer implements SshKeyExchange<SshServerContext>  {
	
	/**
     * The secret value produced during key exchange.
     */
    protected BigInteger secret;

    /**
     * The exchange hash produced during key exchange.
     */
    protected byte[] exchangeHash;

    /**
     * The server's host key.
     */
    protected byte[] hostKey;

    /**
     * The signature generated over the exchange hash
     */
    protected byte[] signature;

    protected String clientId;
    protected String serverId;
    protected byte[] clientKexInit;
    protected byte[] serverKexInit;
    protected SshPrivateKey prvkey;
    protected SshPublicKey pubkey;
    protected boolean firstPacketFollows;
    protected boolean useFirstPacket;
    boolean sentNewKeys = false;
    boolean receivedNewKeys = false;

    /**
     * The transport protocol for sending/receiving messages
     */
    protected SshTransport<SshServerContext> transport;

    String hashAlgorithm;
    private final SecurityLevel securityLevel;
    final int priority;
    /**
     * Contruct an uninitialized key exchange
     */
    public SshKeyExchangeServer(String hashAlgorithm, SecurityLevel securityLevel, int priority) {
    	this.hashAlgorithm = hashAlgorithm;
    	this.securityLevel = securityLevel;
    	this.priority = priority;
    }
    
    public SecurityLevel getSecurityLevel() {
    	return securityLevel;
    }

    public int getPriority() {
    	return priority;
    }
    
    public void setReceivedNewKeys(boolean receivedNewKeys) {
        this.receivedNewKeys = receivedNewKeys;
    }

    public void setSentNewKeys(boolean sentNewKeys) {
        this.sentNewKeys = sentNewKeys;
    }

    public boolean hasSentNewKeys() {
        return sentNewKeys;
    }

    public boolean hasReceivedNewKeys() {
        return receivedNewKeys;
    }
    
    /* (non-Javadoc)
	 * @see com.maverick.sshd.components.SshKeyExchangeServer#getHashAlgorithm()
	 */
    public String getHashAlgorithm() {
    	return hashAlgorithm;
    }

    /**
     * Get the key exchange algorithm name.
     * 
     * @return the key exchange algorithm.
     */
    public abstract String getAlgorithm();

    /* (non-Javadoc)
	 * @see com.maverick.sshd.components.SshKeyExchangeServer#getExchangeHash()
	 */
    public byte[] getExchangeHash() {
        return exchangeHash;
    }

    /* (non-Javadoc)
	 * @see com.maverick.sshd.components.SshKeyExchangeServer#getHostKey()
	 */
    public byte[] getHostKey() {
        return hostKey;
    }

    /* (non-Javadoc)
	 * @see com.maverick.sshd.components.SshKeyExchangeServer#getSecret()
	 */
    public BigInteger getSecret() {
        return secret;
    }

    /* (non-Javadoc)
	 * @see com.maverick.sshd.components.SshKeyExchangeServer#getSignature()
	 */
    public byte[] getSignature() {
        return signature;
    }

    
    /**
     * Process a key exchange message
     * 
     * @param msg
     * @return boolean, indicating whether it has processed the message or not
     * @throws IOException
     */
    public abstract boolean processMessage(byte[] msg) throws SshException, IOException;

    /**
     * Reset the key exchange.
     */
    public void reset() {
        exchangeHash = null;
        hostKey = null;
        signature = null;
        secret = null;
    }
   
}
