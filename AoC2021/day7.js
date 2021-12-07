const { day7: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const part1 = (input) => {
  const vals = input.split(',').map(Number);
  const uniqVals = new Set(vals);
  const distances = [];
  for (const destination of uniqVals) {
    let counter = 0;
    for (let i = 0; i < vals.length; i++) {
      counter += Math.abs(vals[i] - destination);
    }
    distances.push(counter);
  }
  return Math.min(...distances);
};

const getFuel = (destination, start, cache) => {
  if (cache[Math.abs(destination - start)]) return cache[Math.abs(destination - start)];
  let count = 0;
  for (let i = 0; i < Math.abs(destination - start); i++) {
    if (cache[i]) count = cache[i];
    count += i + 1;
    cache[i + 1] = count;
  }
  cache[Math.abs(destination - start)] = count;

  return count;
};

const part2 = (input) => {
  const vals = input.split(',').map(Number);
  const distances = [];
  const cache = {};
  for (let min = Math.min(...vals); min <= Math.max(...vals); min++) {
    let counter = 0;
    for (let i = 0; i < vals.length; i++) {
      counter += getFuel(min, vals[i], cache);
    }
    distances.push(counter);
  }
  return Math.min(...distances);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
