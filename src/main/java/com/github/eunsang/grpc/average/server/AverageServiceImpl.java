package com.github.eunsang.grpc.average.server;

import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import com.proto.average.AverageServiceGrpc;
import io.grpc.stub.StreamObserver;

public class AverageServiceImpl extends AverageServiceGrpc.AverageServiceImplBase {
    private AverageResponse averageResponse;
    private int result;
    private int count;

    @Override
    public StreamObserver<AverageRequest> average(StreamObserver<AverageResponse> responseObserver) {
        result = 0;
        count = 0;
        StreamObserver<AverageRequest> requestObserver = new StreamObserver<AverageRequest>() {
            @Override
            public void onNext(AverageRequest value) {
                System.out.println("got client message");

                count += 1;
                result += value.getNumber();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("got client error");
            }

            @Override
            public void onCompleted() {
                System.out.println("got client termination signal");

                averageResponse = AverageResponse.newBuilder()
                        .setResult(result / count)
                        .build();

                responseObserver.onNext(averageResponse);

                responseObserver.onCompleted();
                System.out.println("terminated communication with client");
            }
        };

        return requestObserver;
    }
}
