const { day3: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');

// "10011" -> [1, 0, 0, 1, 1]
const strToDecArr = (binStr) => binStr.split('').map((val) => parseInt(val, 10));

// [1,0,1,0] -> [0,1,0,1]
const flipBits = (arr) => arr.map((bit) => (bit === 1 ? 0 : 1));

// [1,0,1,0] -> 10
const bitArrToDec = (arr) => parseInt(arr.join(''), 2);

// arrA - [1,0,1,1], arrB - [0,1,1,1] -> [1,1,2,2]
const sumBitArrs = (arrA, arrB) => arrA.map((val, index) => val + arrB[index]);

//  arr - [[1,0,1,0],
//         [0,1,1,1],
//         [1,0,0,1]]
//     ->  [2,1,2,2]
const sumAllBitArrs = (arr) => arr.reduce((prev, curr) => sumBitArrs(prev, curr));

// [4,1,2,3], 4 -> [1,0,0,1]
const getGamma = (bitSumArr, inputLen) => bitSumArr
  .map((val) => Math.round(val / inputLen));

const part1 = (input) => {
  const lines = input.split('\n');
  const inputDec = lines.map(strToDecArr);
  const bitSumArr = sumAllBitArrs(inputDec);
  const gamma = getGamma(bitSumArr, lines.length);
  const epsilon = flipBits(gamma);
  return bitArrToDec(gamma) * bitArrToDec(epsilon);
};

const getRating = (inputDec, type) => {
  let output = [...inputDec];
  const len = output[0].length;

  for (let i = 0; i < len; i++) {
    const bitSumArr = sumAllBitArrs(output);
    output = output.filter((val, _, arr) => {
      if (Math.round(arr.length / 2) <= bitSumArr[i]) { return type === 'oxygen' ? val[i] === 1 : val[i] === 0; }
      return type === 'oxygen' ? val[i] === 0 : val[i] === 1;
    });
    if (output.length === 1) break;
  }
  return output[0];
};

const part2 = (input) => {
  const lines = input.split('\n');
  const inputDec = lines.map(strToDecArr);
  const oxygenRating = getRating(inputDec, 'oxygen');
  const scrubberRating = getRating(inputDec, 'scrubber');
  return bitArrToDec(oxygenRating) * bitArrToDec(scrubberRating);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
