
let input = require('./day6_input');

let groups = input.split('\n\n');

let partA = (()=>{
    let count = 0;
    for(let group of groups){
        let answers = [];
        let people = group.split(`\n`);
        for(let person of people){
            let questionIndex = 0;
            let personAnswers = person.split('');
            for(let personAnswer of personAnswers){
                if(!answers.includes(personAnswer))
                    answers.push(personAnswer);
                questionIndex++;
            }
        }
        count += answers.length;
    }
    console.log('A', count);
})();



let partB = (()=>{
    let count = 0;
    for(let group of groups){
        let answers = [];
        let people = group.split(`\n`);
        let personIndex = 0;
        for(let person of people){
            let personAnswers = person.split('');
            if(personIndex===0) answers = personAnswers;
            else answers = answers.filter(v=>personAnswers.includes(v));
            personIndex++;
        }
        count += answers.length;
    }
    console.log('B', count);
})();
