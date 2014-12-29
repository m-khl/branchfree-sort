branchfree-sort
===============

silly idea to sort without braching, but by calculating rank per elem 


bench result rejects this idea
```
BranchFreeSortTest.testSort10: [measured 1000000 out of 1001000 rounds, threads: 1 (sequential)]
 round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 3, GC.time: 0.08, time.total: 1.75, time.warmup: 0.02, time.bench: 1.73
BranchFreeSortTest.testBranchFree10: [measured 1000000 out of 1001000 rounds, threads: 1 (sequential)]
 round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 1, GC.time: 0.02, time.total: 1.47, time.warmup: 0.01, time.bench: 1.46
BranchFreeSortTest.testBranchFree100: [measured 1000000 out of 1001000 rounds, threads: 1 (sequential)]
 round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 1, GC.time: 0.02, time.total: 7.46, time.warmup: 0.01, time.bench: 7.45
BranchFreeSortTest.testSort100: [measured 1000000 out of 1001000 rounds, threads: 1 (sequential)]
 round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 2, GC.time: 0.06, time.total: 2.86, time.warmup: 0.01, time.bench: 2.86
```

Test              | 1M round time, sec
------------------|-------------------
testBranchFree100 |  time.bench: 7.45 
testSort100       | time.bench: 2.86 
