package com.example.transactions.demo.transactions;


import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@SpringBootTest
class DemoTransactionsApplicationTests {


	
//    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

//    @BeforeEach
//    void setUp() {
//        transactionTemplate = new TransactionTemplate(transactionManager);
//    }
	
	
	@Test
	void givenAPayment_WhenNotDuplicate_ThenShouldCommit() {
	    Long id = transactionTemplate.execute(status -> {
	        Payment payment = new Payment();
	        payment.setAmount(1000L);
	        payment.setReferenceNumber("Ref-1");
	        payment.setState(Payment.State.SUCCESSFUL);

	        entityManager.persist(payment);

	        return payment.getId();
	    });

	    Payment payment = entityManager.find(Payment.class, id);
	    assertThat(payment).isNotNull();
	}
	
	@Test
	void givenTwoPayments_WhenRefIsDuplicate_ThenShouldRollback() {
	    try {
	        transactionTemplate.execute(status -> {
	            Payment first = new Payment();
	            first.setAmount(1000L);
	            first.setReferenceNumber("Ref-1");
	            first.setState(Payment.State.SUCCESSFUL);

	            Payment second = new Payment();
	            second.setAmount(2000L);
	            second.setReferenceNumber("Ref-1"); // same reference number
	            second.setState(Payment.State.SUCCESSFUL);

	            entityManager.persist(first); // ok
	            entityManager.persist(second); // fails

	            return "Ref-1";
	        });
	    } catch (Exception ignored) {}

	    assertThat(entityManager.createQuery("select p from Payment p").getResultList()).isEmpty();
	}
	
	@Test
	void givenAPayment_WhenMarkAsRollback_ThenShouldRollback() {
	    transactionTemplate.execute(status -> {
	        Payment payment = new Payment();
	        payment.setAmount(1000L);
	        payment.setReferenceNumber("Ref-1");
	        payment.setState(Payment.State.SUCCESSFUL);

	        entityManager.persist(payment);
	        status.setRollbackOnly();

	        return payment.getId();
	    });

	    assertThat(entityManager.createQuery("select p from Payment p").getResultList()).isEmpty();
	}
	
	@Test
	void givenAPayment_WhenNotExpectingAnyResult_ThenShouldCommit() {
	    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
	        @Override
	        protected void doInTransactionWithoutResult(TransactionStatus status) {
	            Payment payment = new Payment();
	            payment.setReferenceNumber("Ref-1");
	            payment.setState(Payment.State.SUCCESSFUL);

	            entityManager.persist(payment);
	        }
	    });

	    assertThat(entityManager.createQuery("select p from Payment p").getResultList()).hasSize(1);
	}

	
	@Test
	void givenAPayment_WhenUsingTxManager_ThenShouldCommit() {
	 
	    // transaction definition
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
		definition.setTimeout(3);


	    TransactionStatus status = transactionManager.getTransaction(definition);
	    try {
	        Payment payment = new Payment();
	        payment.setReferenceNumber("Ref-1");
	        payment.setState(Payment.State.SUCCESSFUL);

	        entityManager.persist(payment);
	        transactionManager.commit(status);
	    } catch (Exception ex) {
	        transactionManager.rollback(status);
	    }

	    assertThat(entityManager.createQuery("select p from Payment p").getResultList()).hasSize(1);
	}
}
