
// let input = `L.LL.LL.LL
// LLLLLLL.LL
// L.L.L..L..
// LLLL.LL.LL
// L.LL.LL.LL
// L.LLLLL.LL
// ..L.L.....
// LLLLLLLLLL
// L.LLLLLL.L
// L.LLLLL.LL`;

let input = require('./day11_input');

function countOccupiedAdjacentSeats(x,y){
    let validField = (adjX,adjY) => adjX >= 0 && adjX < grid[0].length && adjY >= 0 && adjY < grid.length;
    let adjacentSeatsRelative = [[-1,-1],[0,-1],[1,-1],[-1,0],[1,0],[-1,1],[0,1],[1,1]];
    let adjacentSeatsVals = [];
    for(let [adjX, adjY] of adjacentSeatsRelative){
        if(!validField(x+adjX, y+adjY)) continue;
        adjacentSeatsVals.push(grid[adjY+y][adjX+x])
    }
    return adjacentSeatsVals.reduce((prev, curr)=>{
        if(curr==='#') prev++;
        return prev;
    },0);
}

function gridToStr(grid){
    return grid.reduce((prev, curr)=>{
        prev.push(curr.join('')+'\n');
        return prev;
    },[]).join('');
}

function countOccupiedSeats(){
    return grid.reduce((prev, curr)=>{
        prev+=curr.reduce((p,c)=>{
            if(c==='#') p++;
            return p;
        },0)
        return prev;
    },0)
}

let grid = input.split('\n').map(v=>v.split(''));
let gridStr = gridToStr(grid);
let prevGridStr = null;
let rounds = 0;

while(prevGridStr!==gridStr){
    let newGrid = Array(grid.length).fill(0).map(a=> Array(grid[0].length).fill(0));
    for(let i=0; i<grid.length; i++){
        for(let j=0; j<grid[0].length; j++){
            let val = grid[i][j];
            let occupiedSeats = countOccupiedAdjacentSeats(j, i);
            if(val === 'L' && occupiedSeats === 0) newGrid[i][j] = '#';
            else if(val === '#' && occupiedSeats > 3) newGrid[i][j] = 'L';
            else newGrid[i][j] = val;
        }
    }
    prevGridStr = gridToStr(grid);
    gridStr = gridToStr(newGrid);
    grid = newGrid;
    rounds++;
}

console.log(rounds);
console.log(countOccupiedSeats());




// If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
// If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
// Otherwise, the seat's state does not change.