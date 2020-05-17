let input = require("./input");
let { day, splitCommasIntoNumberArray } = require("../utils");

function printGrid(grid) {
  let cpy = JSON.parse(JSON.stringify(grid));
  let out = cpy.reduce(
    (acc, curr) => acc + curr.reduce((acc2, curr2) => acc2 + (curr2 === 0 ? " " : curr2), "") + "\n",
    ""
  );
  console.log(out);
}

function writePosition({ relativeBase }, parameter) {
  let { mode, value } = parameter;
  switch (mode) {
    case 0:
      return value;
    case 1:
      console.log(
        "Something went wrong when getting writePosition for",
        parameter
      );
      break;
    case 2:
      return value + relativeBase;
  }
}

function readValue({ intcode, relativeBase }, parameter) {
  let { mode, value } = parameter;
  switch (mode) {
    case 0:
      return intcode[value] || 0;
    case 1:
      return value;
    case 2:
      return intcode[value + relativeBase] || 0;
  }
  console.log("Something went wrong when reading", parameter);
}

function executeInstruction(state, methodSignature, output) {
  let { intcode, inputs } = state;
  let [param1, param2, param3] = methodSignature.parameters;
  let { opcode } = methodSignature;
  let status = 0;
  let instructions = {
    "01": () => {
      intcode[writePosition(state, param3)] =
        readValue(state, param1) + readValue(state, param2);
      offset = 4;
    },
    "02": () => {
      intcode[writePosition(state, param3)] =
        readValue(state, param1) * readValue(state, param2);
      offset = 4;
    },
    "03": () => {
      if (inputs.length > 0) {
        intcode[writePosition(state, param1)] = inputs.shift();
        offset = 2;
      } else offset = 0;
    },
    "04": () => {
      output.push(readValue(state, param1));
      offset = 2;
    },
    "05": () => {
      if (readValue(state, param1) != 0) {
        state.index = readValue(state, param2);
        offset = 0;
      } else offset = 3;
    },
    "06": () => {
      if (readValue(state, param1) == 0) {
        state.index = readValue(state, param2);
        offset = 0;
      } else offset = 3;
    },
    "07": () => {
      intcode[writePosition(state, param3)] = Number(
        readValue(state, param1) < readValue(state, param2)
      );
      offset = 4;
    },
    "08": () => {
      intcode[writePosition(state, param3)] = Number(
        readValue(state, param1) == readValue(state, param2)
      );
      offset = 4;
    },
    "09": () => {
      state.relativeBase += readValue(state, param1);
      offset = 2;
    },
    "99": () => {
      status = 1;
      offset = 0;
    }
  };
  if (!instructions[opcode]) {
    console.log("something went wrong", opcode);
    status = -1;
    offset = 0;
  } else instructions[opcode]();
  state.index += offset;
  return status;
}

function getMethodSignature(intcode) {
  let [instruction, param1, param2, param3] = intcode;
  instruction = instruction
    .toString()
    .split("")
    .reverse();
  let [E = 0, D = 0, C = 0, B = 0, A = 0] = instruction;
  return {
    parameters: [
      {
        mode: parseInt(C),
        value: param1
      },
      {
        mode: parseInt(B),
        value: param2
      },
      {
        mode: parseInt(A),
        value: param3
      }
    ],
    opcode: `${D}${E}`
  };
}

function executeProgram(state, grid, play = false) {
  let { intcode } = state;
  let output = [];
  let score = 0;
  let ball = { x: 0, y: 0 };
  let paddle = { x: 0, y: 0 };
  while (state.index < intcode.length - 1) {
    let methodSignature = getMethodSignature(
      intcode.slice(state.index, state.index + 4)
    );
    let status = executeInstruction(state, methodSignature, output);
    if (output.length == 3) {
      let [x, y, tileId] = output;
      if (x == -1 && y == 0) {
        score = tileId;
      } else {
        grid[y][x] = tileId;
        if (tileId == 4) {
          ball.x = x;
          ball.y = y;
        }
        if (tileId == 3) {
          paddle.x = x;
          paddle.y = y;
        }
      }
      if (play) {
        if (ball.x == paddle.x) state.inputs = [0];
        else if (ball.x > paddle.x) state.inputs = [1]
        else state.inputs = [-1];
      }

      output = [];
    }
    if (status == 1 || status == -1) break;
  }
  if (play) return score;
  return grid;
}

function countChar(grid, char) {
  return grid.reduce((acc, row) => acc + row.reduce((acc2, val) => acc2 + (val == char ? 1 : 0), 0), 0)
}

function part1(code, inputs = []) {
  let grid = Array(50).fill(0).map(() => Array(50).fill(0));
  let intcode = splitCommasIntoNumberArray(code);
  grid = executeProgram({ intcode, index: 0, inputs, relativeBase: 0 }, grid);
  return countChar(grid, 2);
}

function part2(code, inputs = []) {
  let grid = Array(50).fill(0).map(() => Array(50).fill(0));
  let intcode = splitCommasIntoNumberArray(code);
  intcode[0] = 2;
  return executeProgram({ intcode, index: 0, inputs, relativeBase: 0 }, grid, true);
}

day(() => part1(input, []), () => part2(input))


