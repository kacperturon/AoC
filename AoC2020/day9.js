// let input = `35
// 20
// 15
// 25
// 47
// 40
// 62
// 55
// 65
// 95
// 102
// 117
// 150
// 182
// 127
// 219
// 299
// 277
// 309
// 576`;

let input = require('./day9_input');
// const preamble = 5;
const preamble = 25;

let startingIndex = preamble+1;

let values = input.split('\n').map(Number);

function getSums(startIndex, endIndex){
    let range = values.slice(startIndex,endIndex+1);
    let sums = [];
    for(let i=0; i<range.length; i++){
        for(let j=i+1; j<range.length; j++){
            if(!sums.includes(range[i]+range[j]))
                sums.push(range[i]+range[j]);
        }
    }
    return sums;
}

let val;

for(let i=preamble; i<values.length; i++){
    val = values[i];
    if(!getSums(i-preamble, i-1).includes(val)){
        console.log('A', val);
        break;
    }
}


for(let i=0; i<values.length; i++){
    let s = 0;
    let vals = [];
    for(let j=i; j<values.length; j++){
        s+=values[j];
        vals.push(values[j])
        if(s===val) break;
    }
    if(s===val){
        console.log('B', Math.max(...vals)+Math.min(...vals));
        break;
    }
}
