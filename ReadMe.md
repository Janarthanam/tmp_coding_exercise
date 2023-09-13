# Efficient Range Queries
Generally applications are built on a platform that supports large volumes of data stored in a distributed database. Let's consider in-memory database for simplicity here. We would like to enable our app developers to ask questions like:

`Find workers Where salary > 10 and salary < 20`

It’s obviously important to get answers quickly. We expect to have millions and billions of records. To support (amongst other things) the parallelization of queries on this database we will partition the data into containers/shards.

A typical query request, such as Find payResultLines where net > 10,000 and net < 100,000 would be processed as so:
1. A query request is dispatched to a computation node(s) that houses several containers.
2. The computation node(s) in turn dispatches a job into a local threadpool. Each job invokes findIdsInRange(10,000, 100,000, false, false) on a data container.
3. The jobs are executed with results joined together by the query processor and returned to caller.

## Requirements

Implement a Container to hold numeric values, and answer basic range queries. The API to implement is given in the package.

## Questions

1. Can't we just use a B-Tree/ NavigableMap/HashTable/BinarySearch/Lucene
   Sure, if you want. But there are a number of things we'd like to achieve:
- The container will be in memory and will need to hold lots of data, so we'd like it to be as space efficient as possible, but not to the extent where speed is significantly compromised. Speed is king, memory/disk can be purchased.
- Since we hate collecting garbage, we need to be careful about memory allocation during a query, as my grandmother told me, "allocate not, gc not".
- Code should be clear and understandable.
- The rough order of trade off to consider for this case is: search performance, efficient usage of memory, simplicity and maintainability of the code

These objectives are often at odds - we'd like to explore what's possible.

2. What about Big O? Should it be O(log n) or O(log log n)?
   We partition the data into Containers of 32K instances each. So, Big O analysis is sort of useless here, as we're not concerned about how the algorithm grows, only with how it performs when n=32K.

