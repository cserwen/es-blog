let nodes= [{level:0, id:"一"},{level:1, id:"1"},{level:2, id:"（1）"},{level:0, id:"而"},{level:1, id:"1"},{level:2, id:"（1）"},{level:0, id:"而"}]

let tocData = {
    id: '',
    children: []
}
let i = 0
function buildToc(nodes, currentLevel, baseLevel)
{
    for (; i < nodes.length; i++) {
        let node = nodes[i]
        let level = node.level
        let local = tocData
        for (let j = 0; j < currentLevel-baseLevel; j++) {
            local = tocData.children[tocData.children.length-1]
        }
        let toc = {
            label: node.id,
            children: []
        }
        if (currentLevel === level ){
            local.children.push(toc)
        } else if (currentLevel < level ) {
            buildToc(nodes,currentLevel+1, baseLevel)
        }else {
            buildToc(nodes, currentLevel-1, baseLevel)
        }
    }
}

buildToc(nodes,  0, 0 ,0, 0)
console.log(tar.toString())