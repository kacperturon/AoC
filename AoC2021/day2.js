const { realData } = require('./day2_input');

const lines = realData.split('\n');

const part1 = () => {
  let counts = {
    up: 0,
    down: 0,
    forward: 0,
  };
  
  for(let i=0; i<lines.length; i++){
    let [cmd, val] = lines[i].split(' ');
    counts[cmd]+=Number(val);
  }
  console.log('output', Math.abs(counts.down-counts.up)*counts.forward)
}


const part2 = () => {
  let counts = {
    up: 0,
    down: 0,
    forward: 0,
    aim: 0,
    depth: 0
  };

  for(let i=0; i<lines.length; i++){
    let [cmd, valStr] = lines[i].split(' ');
    const val = Number(valStr);
    switch(cmd){
      case 'down':
        counts.down += val;
        counts.aim += val;
        break;
      case 'up':
        counts.up += val;
        counts.aim -= val;
        break;
      case 'forward':
        counts.forward += val;
        counts.depth += counts.aim * val;
        break;
    }
  }

  console.log('output', counts.forward * counts.depth);
}


part1();
part2();