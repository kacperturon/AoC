const { day10: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

const getCorruptChar = (line) => {
  const chars = line.split('');
  const stack = [];
  for (let i = 0; i < chars.length; i++) {
    if (chars[i] === ')' || chars[i] === '}' || chars[i] === ']' || chars[i] === '>') {
      if (stack.length === 0) return chars[i];
      if (stack[stack.length - 1] === '(' && chars[i] !== ')') return chars[i];
      if (stack[stack.length - 1] === '{' && chars[i] !== '}') return chars[i];
      if (stack[stack.length - 1] === '<' && chars[i] !== '>') return chars[i];
      if (stack[stack.length - 1] === '[' && chars[i] !== ']') return chars[i];
      stack.pop();
    } else stack.push(chars[i]);
  }
  return null;
};

const charPoints = {
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137,
};

const part1 = (input) => {
  const lines = input.split('\n');
  const corruptedLines = lines.map(getCorruptChar).filter((val) => val !== null);
  const score = corruptedLines.reduce((prev, curr) => prev += charPoints[curr], 0);
  return score;
};

const getCompletionString = (line) => {
  const chars = line.split('');
  const stack = [];
  for (let i = 0; i < chars.length; i++) {
    if (chars[i] === ')' || chars[i] === '}' || chars[i] === ']' || chars[i] === '>') {
      if (stack.length === 0) return chars[i];
      if (stack[stack.length - 1] === '(' && chars[i] !== ')') return chars[i];
      if (stack[stack.length - 1] === '{' && chars[i] !== '}') return chars[i];
      if (stack[stack.length - 1] === '<' && chars[i] !== '>') return chars[i];
      if (stack[stack.length - 1] === '[' && chars[i] !== ']') return chars[i];
      stack.pop();
    } else stack.push(chars[i]);
  }
  const newChars = [];
  while (stack.length > 0) {
    if (stack[stack.length - 1] === '(') newChars.push(')');
    if (stack[stack.length - 1] === '[') newChars.push(']');
    if (stack[stack.length - 1] === '{') newChars.push('}');
    if (stack[stack.length - 1] === '<') newChars.push('>');
    stack.pop();
  }
  return newChars.join('');
};

const getScore = (completionStr) => {
  const charToScore = {
    ')': 1,
    ']': 2,
    '}': 3,
    '>': 4,
  };
  const chars = completionStr.split('');
  let score = 0;
  for (let i = 0; i < chars.length; i++) {
    score *= 5;
    score += charToScore[chars[i]];
  }
  return score;
};

const part2 = (input) => {
  const lines = input.split('\n');
  const incompleteLines = lines.filter((val) => getCorruptChar(val) === null);
  const completionStrs = incompleteLines.map(getCompletionString);
  const scores = completionStrs.map(getScore).sort((a, b) => a - b);
  return scores[Math.floor(scores.length / 2)];
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
