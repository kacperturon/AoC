let input = require("./input");
let { day, splitCommasIntoNumberArray, sleep } = require("../utils");

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

function printGrid(grid, robot){
  let gridCpy = JSON.parse(JSON.stringify(grid));
  gridCpy[robot.y][robot.x] = 'D';
  let out = "";

  out = gridCpy.reduce((acc, val)=>acc+val.reduce((acc2, val2)=>acc2+val2,"")+"\n", "")
  console.log(out);
}

function executeProgram(state) {
  let { intcode } = state;
  let output = [];
  while (state.index < intcode.length - 1) {
    let methodSignature = getMethodSignature(
      intcode.slice(state.index, state.index + 4)
    );
    let status = executeInstruction(state, methodSignature, output);
    if(output.length>0){
      return output;
    }
    if (status == 1 || status == -1) break;
  }
  return output;
}


async function part1(code, inputs = []) {
  let gridSize = 400;
  let grid = Array(gridSize).fill(' ').map(()=>new Array(gridSize).fill(' '));
  let robot = {
    x: gridSize/2,
    y: gridSize/2
  }
  let intcode = splitCommasIntoNumberArray(code);
  return await BFS({ intcode, index: 0, inputs, relativeBase: 0 },robot, grid, 'O');
}
(async()=>{
  console.log(await part1(input));

})();
async function BFS(state, robot, grid, target){
  let moves = [];
  let visited = {};
  let x = robot.x, y = robot.y;
  do {
      let xNeighbours = [0,0,-1,1];
      let yNeighbours = [-1,1,0,0];

      for(let i=0; i< xNeighbours.length; i++){
        
          let newX = x+xNeighbours[i];
          let newY = y+yNeighbours[i];
          
          // execute code
          if(validMove(grid, newX, newY, visited, moves))
          {
            state.inputs.push(i+1);
            let output = executeProgram(state);

            if(output.length == 1){
              let statusCode = output.shift();
              grid[newY][newX] = statusCode == 0 ? '#' : statusCode == 1 ? '.' : 'O';
              if(grid[newY][newX]!='#')
              moves.push([newX, newY]);
              visited[newY+','+newX]=1;
              // printGrid(grid, robot);

            }
  

          }
      }
      if(moves.length >0){
        [x,y] = moves.shift();
        robot.x = x;
        robot.y = y;
        if(grid[y][x]==target) {
          return [x,y];
       };
      }
      else{
        console.log('end');
      }
      // await sleep(1);
      
      printGrid(grid, robot);

 
  } while(true)

}

function validMove(arr, x,y, visited, moves){
  return (x>=0 && y>=0 && x<arr[0].length && y<arr.length 
     && !visited[y+','+x] 
    && moves.filter(v=>v[0]==x && v[1]==y).length==0);
}


//   day(
//   () => part1(input, [1]),
//   () => part1(input, [2])
// );
