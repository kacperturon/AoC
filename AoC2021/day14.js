const { day14: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const parseInstructions = (instructions) => instructions.reduce((prev, curr) => {
  const [from, to] = curr.split(' -> ');
  prev[from] = to;
  return prev;
}, {});

const executeStep = (str, instructionSet) => {
  let newStr = '';
  for (let i = 0; i < str.length; i += 1) {
    const substr = str.substr(i, 2);
    const substrArr = substr.split('');
    if (instructionSet[substr]) {
      newStr += substrArr[0] + instructionSet[substr];
    } else newStr += substr;
  }
  return newStr;
};

const charCount = (str) => {
  const counts = str.split('').reduce((prev, curr) => {
    if (prev[curr]) prev[curr] += 1;
    else prev[curr] = 1;
    return prev;
  }, {});
  const maxNum = Math.max(...Object.values(counts));
  const maxKey = Object.keys(counts).find((key) => counts[key] === maxNum);
  const minNum = Math.min(...Object.values(counts));
  const minKey = Object.keys(counts).find((key) => counts[key] === minNum);
  return [maxKey, maxNum, minKey, minNum];
};

const part1 = (input) => {
  const [startStr, ...instructions] = input.split('\n').filter((val) => val !== '');
  const instructionSet = parseInstructions(instructions);
  let str = startStr.trim();
  for (let i = 0; i < 10; i++) {
    str = executeStep(str, instructionSet);
  }
  const count = charCount(str);
  return count[1] - count[3];
};

const getPairCounts = (str) => {
  const counts = {};
  const strArr = str.split('');
  for (let i = 0; i < str.length - 1; i += 1) {
    const key = strArr[i] + strArr[i + 1];
    counts[key] = counts[key] ? counts[key] + 1 : 1;
  }
  return counts;
};

const transform = (pair, instructionSet) => [pair[0] + instructionSet[pair],
  instructionSet[pair] + pair[1]];

function grow(pairs, map) {
  const nextPairs = {};
  for (const pair of Object.keys(pairs)) {
    const count = pairs[pair];
    const [pairA, pairB] = transform(pair, map);
    nextPairs[pairA] = nextPairs[pairA] ? nextPairs[pairA] + count : count;
    nextPairs[pairB] = nextPairs[pairB] ? nextPairs[pairB] + count : count;
  }
  return nextPairs;
}

const letterCounts = (pairs, last) => {
  const counts = {};
  for (const pair of Object.keys(pairs)) {
    const [letter1] = pair.split('');
    const val = pairs[pair];
    counts[letter1] = counts[letter1] ? counts[letter1] + val : val;
    if (pair === last) {
      const lastChar = last[1];
      counts[lastChar] = counts[lastChar] ? counts[lastChar] + 1 : 1;
    }
  }
  const out = [...Object.keys(counts)].sort((a, b) => counts[a] - counts[b]);
  return [out[out.length - 1], counts[out[out.length - 1]], out[0], counts[out[0]]];
};

const part2 = (input) => {
  const [startStr, ...instructions] = input.split('\n').filter((val) => val !== '');
  let last = startStr.slice(-2);
  const instructionSet = parseInstructions(instructions);
  let pairCount = getPairCounts(startStr);
  for (let i = 0; i < 40; i++) {
    pairCount = grow(pairCount, instructionSet);
    [, last] = transform(last, instructionSet);
  }
  const letterCount = letterCounts(pairCount, last);
  return letterCount[1] - letterCount[3];
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
