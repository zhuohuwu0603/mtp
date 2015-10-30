package com.vedri.mtp.consumption.http.akka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedri.mtp.consumption.MtpConsumptionConstants;
import com.vedri.mtp.consumption.http.AbstractHttpServer;
import com.vedri.mtp.consumption.support.kafka.KafkaMessageEnvelope;
import com.vedri.mtp.consumption.transaction.TransactionManager;
import com.vedri.mtp.core.transaction.Transaction;

@Component
public class AkkaHttpServer extends AbstractHttpServer {

	@Value(MtpConsumptionConstants.KAFKA_TOPIC_NAME)
	private String topicName;

	private final ActorSystem akkaSystem;
	private final ObjectMapper transactionObjectMapper;
	private final ObjectMapper objectMapper;
	private final TransactionManager transactionManager;

	private ActorRef consumerActorRef;
	private MtpHttpApp mtpHttpApp;

	@Autowired
	public AkkaHttpServer(ActorSystem akkaSystem,
			@Qualifier("transactionObjectMapper") final ObjectMapper transactionObjectMapper,
			@Qualifier("objectMapper") final ObjectMapper objectMapper,
			final TransactionManager transactionManager) {
		this.akkaSystem = akkaSystem;
		this.transactionObjectMapper = transactionObjectMapper;
		this.objectMapper = objectMapper;
		this.transactionManager = transactionManager;
	}

	@Override
	protected void doStart(ActorRef consumerActorRef) throws Exception {
		this.consumerActorRef = consumerActorRef;
		mtpHttpApp = new MtpHttpApp(this);
		mtpHttpApp.start(getBindHost(), getBindPort(), getPublicProtocol(), getPublicHost(), getPublicPort());
	}

	@Override
	protected void doStop() throws Exception {
		mtpHttpApp.stop();
	}

	ObjectMapper getTransactionObjectMapper() {
		return transactionObjectMapper;
	}

	ActorSystem getAkkaSystem() {
		return akkaSystem;
	}

	Transaction doAddTransaction(Transaction transaction) throws JsonProcessingException {
		Transaction added = transactionManager.addTransaction(transaction);

		final KafkaMessageEnvelope<String, String> kafkaMessageEnvelope = new KafkaMessageEnvelope<>(
				topicName, added.getTransactionId(),
				objectMapper.writeValueAsString(added));
		consumerActorRef.tell(kafkaMessageEnvelope, null);
		return added;
	}

	Transaction doGetTransaction(UUID uuid) {
		return transactionManager.getTransaction(uuid);

	}
}