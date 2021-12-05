const { realData, sampleData } = require('./day1_input');
const { executePart } = require('./helpers');

const part1 = (input) => {
  const lines = input.split('\n').map(Number);
  let countIncrease = 0;
  for (let i = 1; i < lines.length; i += 1) {
    const prev = lines[i - 1];
    const curr = lines[i];
    if (curr > prev) countIncrease++;
  }
  return countIncrease;
};

const part2 = (input) => {
  const lines = input.split('\n').map(Number);
  let countIncrease = 0;

  let prev = 0;
  let sum = 0;

  for (let i = 0; i < 3; i++) sum += lines[i];
  prev = sum;
  for (let i = 0; i < lines.length - 3; i++) {
    sum -= lines[i];
    sum += lines[i + 3];
    if (sum > prev) countIncrease++;
    prev = sum;
  }
  return countIncrease;
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
