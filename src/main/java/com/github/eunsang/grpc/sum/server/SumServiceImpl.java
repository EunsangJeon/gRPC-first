package com.github.eunsang.grpc.sum.server;

import com.proto.sum.Numbers;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceGrpc.SumServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        Numbers numbers = request.getNumbers();
        int first = numbers.getFirst();
        int second = numbers.getSecond();
        int result = first + second;

        SumResponse sumResponse = SumResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(sumResponse);

        responseObserver.onCompleted();
    }
}
