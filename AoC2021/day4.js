const { sampleData, realData } = require('./day4_input');
const { executePart } = require('./helpers');

const parseBoard = (boardLine) => boardLine.split('\n').map((line) => line.split(/ +/).filter((val) => val !== '').map(Number));

const processNumber = (boards, num) => boards
  .map((board) => board
    .map((row) => row
      .map((val) => (val === num ? -1 : val))));

const boardWon = (board) => {
  // check rows
  for (let i = 0; i < 5; i++) {
    if (board[i].join('') === '-1-1-1-1-1') return true;
  }
  // check cols
  for (let i = 0; i < 5; i++) {
    if (board.flatMap((row) => row[i]).join('') === '-1-1-1-1-1') return true;
  }
  return false;
};

const sumUnmarked = (board) => board
  .reduce((prev, curr) => prev + curr
    .reduce((p, c) => (c === -1 ? p : p + c), 0), 0);

const part1 = (input) => {
  const [numberLine, ...boardsLines] = input.split('\n\n');
  let boards = boardsLines.map(parseBoard);
  let winner;
  let winningNumber;
  const numbers = numberLine.split(',').map(Number);
  for (let i = 0; i < numbers.length; i++) {
    boards = processNumber(boards, numbers[i]);
    winner = boards.find(boardWon);
    if (winner) {
      winningNumber = numbers[i];
      break;
    }
  }
  return sumUnmarked(winner) * winningNumber;
};

const part2 = (input) => {
  const [numberLine, ...boardsLines] = input.split('\n\n');
  let boards = boardsLines.map(parseBoard);
  let winnerIndex = [];
  const numbers = numberLine.split(',').map(Number);
  const winners = [];
  const winningNumbers = [];
  for (let i = 0; i < numbers.length; i++) {
    boards = processNumber(boards, numbers[i]);
    winnerIndex = boards
      .map((board, index) => (boardWon(board) ? index : -1))
      .filter((v) => v !== -1 && !winners.includes(v));
    if (winnerIndex.length > 0) {
      winners.push(...winnerIndex);
      winningNumbers.push(numbers[i]);
      if (winners.length === boards.length) break;
    }
  }
  return sumUnmarked(boards[winners[winners.length - 1]])
   * winningNumbers[winningNumbers.length - 1];
};

executePart(sampleData, part1, '1', '2', true);
executePart(sampleData, part2, '2', '2', true);

executePart(realData, part1, '1', '2', false);
executePart(realData, part2, '2', '2', false);
