const getDay = (filename) => {
  const file = /day.{1,3}\.js$/.exec(filename)[0];
  return /\d+/.exec(file)[0];
};

module.exports.executePart = (input, fn, part, filename, isSample = false) => {
  console.log(`Day: ${getDay(filename)}, Part: ${part}, Input: ${isSample ? 'sample data' : 'real data'}`);
  console.time('Execution time');
  const output = fn(input);
  console.timeEnd('Execution time');
  console.log(`Output: ${output}`);
  console.log();
};

module.exports.getDay = getDay;
