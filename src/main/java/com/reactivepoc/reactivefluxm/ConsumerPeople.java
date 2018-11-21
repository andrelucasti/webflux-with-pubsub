package com.reactivepoc.reactivefluxm;


import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.reactivepoc.reactivefluxm.model.People;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConsumerPeople {

        public Flux<People> consumer(){
            return Flux.create( peopleFluxSink -> {

                ProjectSubscriptionName subscription =
                        ProjectSubscriptionName.of("drummer-project", "poc-webflux");


                MessageReceiver receiver = (pubsubMessage, ackReplyConsumer) -> {

                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();

                    People people = gson.fromJson(pubsubMessage.getData().toStringUtf8(), People.class);
                    peopleFluxSink.next(people);
                    ackReplyConsumer.ack();
                };


                try {

                    Subscriber subscriber = Subscriber.newBuilder(subscription, receiver).build();

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
