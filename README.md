# topwords

`topwords` reads a stream of words from standard input and prints a continuously updated word cloud based on the most recent valid words in a sliding window.

## Running the app

Build and stage the app:

sbt clean stage

Run it:

./target/universal/stage/bin/topwords

Run with arguments:

./target/universal/stage/bin/topwords -c 3 -l 2 -w 5

## Command-line options

- `-c` word cloud size, default `10`
- `-l` minimum word length, default `6`
- `-w` sliding window size, default `1000`

## Example

./target/universal/stage/bin/topwords -c 3 -l 2 -w 5

Input:

a b c
aa bb cc
aa bb aa bb

Output:

aa: 2 bb: 2 cc: 1
aa: 2 bb: 2 cc: 1
aa: 2 bb: 2 cc: 1

## Testing

Run the project tests:

sbt "testOnly edu.luc.cs.cs371.topwords.impl.TopWordsProcessorTest edu.luc.cs.cs371.topwords.impl.TopWordsMainTest edu.luc.cs.cs371.topwords.impl.ConsoleObserverTest"

## Coverage

Coverage was measured with scoverage.

Run coverage with:

sbt clean coverage "testOnly edu.luc.cs.cs371.topwords.impl.TopWordsProcessorTest edu.luc.cs.cs371.topwords.impl.TopWordsMainTest edu.luc.cs.cs371.topwords.impl.ConsoleObserverTest" coverageReport

Coverage achieved:
- Statement coverage: `84.78%`
- Branch coverage: `90.00%`

## Scalability

The program uses a bounded sliding window and frequency map, so it runs in constant space relative to the input stream length.

A memory screenshot from a long-running test is included in:

- `doc/scalability-memory.png`

The command used was:

yes helloworld | ./target/universal/stage/bin/topwords > /dev/null

## Logging

The application uses Scala Logging with Logback for diagnostic output.

## Notes

The original `echo` starter files were kept in the repository as reference material. The `topwords` implementation and tests are separate.