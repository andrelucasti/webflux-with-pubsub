package com.reactivepoc.reactivefluxm;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.reactivepoc.reactivefluxm.model.People;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

@Service
public class ConsumerPeople {

    public static final String PUBSUB = "https://www.googleapis.com/auth/pubsub";

    @Value("${pubsub.google_credencial}")
    private String cretencialFile;

        public Flux<People> consumer(){



            return Flux.create( peopleFluxSink -> {

                ProjectSubscriptionName subscription =
                        ProjectSubscriptionName.of("drummer-project", "poc-webflux");


                MessageReceiver receiver = (pubsubMessage, ackReplyConsumer) -> {
                    System.out.println(pubsubMessage.getData().toStringUtf8());
                    try{
                        Gson gson = new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .create();


                        People people = gson.fromJson(pubsubMessage.getData().toStringUtf8(), People.class);
                        ackReplyConsumer.ack();
                        peopleFluxSink.next(people);
                    } catch (Exception me) {
                        peopleFluxSink.error(me);
                        ackReplyConsumer.ack();
                    }


                };


                try {

                    // You can specify a credential file by providing a path to GoogleCredentials.
                    // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
                    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(cretencialFile))
                            .createScoped(Collections.singleton(PUBSUB));

                    Subscriber subscriber = Subscriber
                            .newBuilder(subscription, receiver)
                            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                            .build();

                    subscriber.addListener(
                            new Subscriber.Listener() {
                                @Override
                                public void failed(Subscriber.State from, Throwable failure) {
                                    // Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
                                    System.err.println(failure);

                                    peopleFluxSink.error(failure);
                                }
                            },
                            MoreExecutors.directExecutor());
                    subscriber.startAsync().awaitRunning();

                }catch (Exception e){
                    peopleFluxSink.error(e);
                }

            });
        }

}
