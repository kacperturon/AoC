const input = require('./day1_input');

// eslint-disable-next-line
const testInput = `199
200
208
210
200
207
240
269
260
263`;

const lines = input.split('\n').map(Number);

const part1 = () => {
  let countIncrease = 0;

  for (let i = 1; i < lines.length; i += 1) {
    const prev = lines[i - 1];
    const curr = lines[i];
    if (curr > prev) countIncrease++;
  }

  console.log('part1', countIncrease);
};

const part2 = () => {
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

  console.log('part2', countIncrease);
};

part1();
part2();
