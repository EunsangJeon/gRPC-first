package com.github.eunsang.grpc.decomposition.server;

import com.proto.decomposition.DecompositionRequest;
import com.proto.decomposition.DecompositionResponse;
import com.proto.decomposition.DecompositionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DecompositionServiceImpl
        extends DecompositionServiceGrpc.DecompositionServiceImplBase {

    @Override
    public void decomposition(
            DecompositionRequest request,
            StreamObserver<DecompositionResponse> responseObserver) {
        int number = request.getNumber();
        int k = 2;

        while(number > 1) {
            if (number % k == 0){
                number /= k;
                DecompositionResponse decompositionResponse =
                        DecompositionResponse.newBuilder()
                            .setResult(k)
                            .build();
                responseObserver.onNext(decompositionResponse);
            }
            else {
                k++;
            }
        }
        responseObserver.onCompleted();
    }
}
