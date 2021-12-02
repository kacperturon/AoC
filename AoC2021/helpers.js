module.exports.executePart = (input, fn, part, day, isSample = false) => {
  console.log(`Day: ${day}, Part: ${part}, Input: ${isSample ? 'sample data' : 'real data'}`);
  console.time('Execution time');
  const output = fn(input);
  console.timeEnd('Execution time');
  console.log(`Output: ${output}`);
  console.log();
};
