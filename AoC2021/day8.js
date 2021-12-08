const { day8: { realData, sampleData } } = require('./data');
const { executePart } = require('./helpers');
// 0:      1:      2:      3:      4:
//  aaaa    ....    aaaa    aaaa    ....
// b    c  .    c  .    c  .    c  b    c
// b    c  .    c  .    c  .    c  b    c
//  ....    ....    dddd    dddd    dddd
// e    f  .    f  e    .  .    f  .    f
// e    f  .    f  e    .  .    f  .    f
//  gggg    ....    gggg    gggg    ....

//  5:      6:      7:      8:      9:
//  aaaa    aaaa    aaaa    aaaa    aaaa
// b    .  b    .  .    c  b    c  b    c
// b    .  b    .  .    c  b    c  b    c
//  dddd    dddd    ....    dddd    dddd
// .    f  e    f  .    f  e    f  .    f
// .    f  e    f  .    f  e    f  .    f
//  gggg    gggg    ....    gggg    gggg
const part1 = (input) => {
  const lines = input.split('\n');
  let count = 0;
  for (const line of lines) {
    const [, outputValues] = line.split(' | ');
    count += outputValues.split(' ').map((val) => val.trim()).reduce((prev, val) => ([2, 3, 4, 7].includes(val.length) ? prev + 1 : prev), 0);
  }
  return count;
};

const byLen = (line, len) => [...line[0], ...line[1]].filter((e) => e.length === len);
const strDiff = (s1, s2) => [].concat(s1.split(''), s2.split('')).reduce((diff, l) => (diff += (!s1.includes(l) || !s2.includes(l)) ? l : ''), '');
const strContains = (s1, s2) => s2.split('').reduce((diff, l) => diff += (!s1.includes(l)) ? l : '', '') === '';
const strCommon = (s1, s2) => s2.split('').reduce((common, l) => common += (s1.includes(l)) ? l : '', '');

const deduceSeg = (line) => {
  /* 000
    1   2
     333
    4   5
     666  */
  const seg = Array(7); const nr7 = byLen(line, 3)[0]; const nr1 = byLen(line, 2)[0]; const
    nr4 = byLen(line, 4)[0];
  seg[0] = strDiff(nr1, nr7);
  const sgm13 = strDiff(nr4, nr1); const sgm46 = strDiff('abcdefg', seg[0] + nr1 + sgm13); const
    sgm36 = strDiff(byLen(line, 5).filter((s) => strContains(s, nr7))[0], nr7);
  seg[6] = strCommon(sgm46, sgm36);
  seg[4] = strDiff(seg[6], sgm46);
  seg[3] = strDiff(seg[6], sgm36);
  seg[1] = strDiff('abcdefg', seg.reduce((a, c) => a + c, '') + nr1);
  seg[5] = strDiff(byLen(line, 5)
    .filter((s) => strContains(s, seg[1]))[0], seg[0] + seg[1] + seg[3] + seg[6]);
  seg[2] = strDiff(nr1, seg[5]);
  return seg;
};

const sevenSeg2Num = ['1110111', '0010010', '1011101', '1011011', '0111010', '1101011', '1101111', '1010010', '1111111', '1111011'];

const seg2Num = (on, map, display = Array(7).fill(0)) => {
  on.split('').map((l) => display[map.indexOf(l)] = 1);
  return sevenSeg2Num.indexOf(display.join(''));
};
const part2 = (input) => {
  const data = input.split('\n').map((line) => line.split(' | ').map((l) => l.split(' ')));
  return data.reduce((acc, item) => acc + Number(item[1].reduce((s, n) => s + seg2Num(n, deduceSeg(item)), '')), 0);
};

executePart(sampleData, part1, '1', __filename, true);
executePart(sampleData, part2, '2', __filename, true);

executePart(realData, part1, '1', __filename, false);
executePart(realData, part2, '2', __filename, false);
