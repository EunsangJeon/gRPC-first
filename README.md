## RPC

Remote Procedure Call

## Protocol Buffers

Protocol buffers are Google's language-neutral, platform-neutral, extensible mechanism for serializing structured data – think XML, but smaller, faster, and simpler. You define how you want your data to be structured once, then you can use special generated source code to easily write and read your structured data to and from a variety of data streams and using a variety of languages.

It's binary which means it will be written and read so fast.

## gRPC

gRPC is a modern open source high performance RPC framework that can run in any environment. It can efficiently connect services in and across data centers with pluggable support for load balancing, tracing, health checking and authentication. It is also applicable in last mile of distributed computing to connect devices, mobile applications and browsers to backend services.

- https://grpc.io/
- https://github.com/grpc/grpc-java
- gRPC servers are asynchronous
- gRPC clients can be async or sync. Also clients can perform client side load balancing.
- fun fact: Google has 10 BILLION gRPC requests being made per second internally(2020)
- By default, gRPC strongly advocates for you to use SSL


## Repo Content

- Greeting Service
- Calculator Service
- Unary, Server Streaming, Client Streaming, BiDi Streaming
- Error Handling, Deadlines, SSL Encryption

## HTTP/2

- gRPC leverages HTTP/2 as a backbone for communications
- HTTP/1.1 from 1997, HTTP/2 from 2015. 
- HTTP/2 Single connetion
- HTTP2 supports server push and header compression
- HTTP2 is binary(while 1.1 was text based)
- HTTP2 is secure(SSL TLS)

## Types of API in gRPC

- Unary: classic request/response (HTTP REST like)
- Server Streaming: clinent requests then server gives all the messages (HTTP/2)
- Client Streaming: clinent opens pipe and gives messages and server reponses (HTTP/2)
- Bidirectional Streaming: pipe is opened and work like que and consumer (HTTP/2)

## Intellij

- psvm
- preferences > build, execution, deployment > build tools > gradle > build and run & run test using intellij 

## About setting 

- gradle > Tasks > other > generateProto on src/main/proto/dummy/dummy.proto
- preferences > editor > mouse > you can set ctrl + wheel to manage size of fonts
- You can install gradle packages on Gradle tab on the right side
