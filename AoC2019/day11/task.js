let input = require("./input");
let { day, splitCommasIntoNumberArray } = require("../utils");

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

function executeProgram(state, grid, robot) {
  let { intcode } = state;
  let output = [];
  let panelsPainted = {};
  let sum = 0;
  let i = 1;
  while (state.index < intcode.length - 1) {
    let methodSignature = getMethodSignature(
      intcode.slice(state.index, state.index + 4)
    );
    let status = executeInstruction(state, methodSignature, output);
    if (output.length == 2) {
      let [white, turnLeft] = output;
      grid[robot.y][robot.x] = white ? "#" : ".";
      panelsPainted[robot.y + "," + robot.x] = panelsPainted[
        robot.y + "," + robot.x
      ]
        ? panelsPainted[robot.y + "," + robot.x] + 1
        : 1;
      sum++;
      robot.dir = directionAfterTurn(robot.dir, turnLeft);
      moveRobot(robot);
      // setTimeout(
      //   (() => {
      //     let g = JSON.parse(JSON.stringify(grid));
      //     let r = JSON.parse(JSON.stringify(robot));
      //     return () => printGrid(g, r);
      //   })(),
      //   500 + 100 * i
      // );
      // printGrid(grid, robot);
      i++;
      currentTileWhite = grid[robot.y][robot.x] === "#";
      state.inputs = [currentTileWhite];
      output = [];
    }

    if (status == 1 || status == -1) break;
  }
  return panelsPainted;
}

function printGrid(grid, robot) {
  let cpy = JSON.parse(JSON.stringify(grid));
  let { dir, x, y } = robot;
  let robotSymbol =
    dir === "up" ? "^" : dir === "down" ? "v" : dir === "left" ? "<" : ">";
  cpy[y][x] = robotSymbol;
  let out = cpy.reduce(
    (acc, curr) => acc + curr.reduce((acc2, curr2) => acc2 + curr2, "") + "\n",
    ""
  );

  console.log(out);
}

function moveRobot(robot) {
  let { dir } = robot;
  let x = dir === "left" ? -1 : dir === "right" ? 1 : 0;
  let y = dir === "up" ? -1 : dir === "down" ? 1 : 0;
  robot.x += x;
  robot.y += y;
}
function directionAfterTurn(currentDir, turnLeft) {
  switch (currentDir) {
    case "up":
      return turnLeft ? "left" : "right";
    case "down":
      return turnLeft ? "right" : "left";
    case "left":
      return turnLeft ? "down" : "up";
    case "right":
      return turnLeft ? "up" : "down";
  }
}

function part1(code, inputs = []) {
  let grid = Array(150)
    .fill(".")
    .map(() => Array(150).fill("."));

  let intcode = splitCommasIntoNumberArray(code);
  return Object.entries(
    executeProgram({ intcode, index: 0, inputs, relativeBase: 0 }, grid, {
      x: grid.length / 2,
      y: grid.length / 2,
      dir: "up"
    })
  ).length;
}

function part2(code, inputs = []) {
  let grid = Array(20)
    .fill(".")
    .map(() => Array(100).fill("."));
  let robot = {
    x: grid[0].length / 2,
    y: grid.length / 2,
    dir: "up"
  };
  let intcode = splitCommasIntoNumberArray(code);
  Object.entries(
    executeProgram({ intcode, index: 0, inputs, relativeBase: 0 }, grid, robot)
  );
  printGrid(grid, robot);
}

day(() => part1(input, [0]), () => part2(input, [1]))
