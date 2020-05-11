//User functions:
async function downloadFile() {

    const input = document.getElementById('filenameField').value;

    //Resolving the correct IP adress where the file should be located
    const nodeIP = await fetch('http://localhost:8080/user/location?filename='.concat(input))
        .then(nodeIP => {
            if (nodeIP.status == 204) {
                throw new Error('There are no nodes in the network')
            } else if (!nodeIP.ok) {
                throw new Error('Something is wrong with the connection!')
            }
            return nodeIP.text();
        }).catch(error => {
            console.error('Something went wrong:', error);
            window.alert(error);
        });

    //Showing the correct node:
    let label = document.getElementById("downloadNode");
    label.innerHTML = ('The file is downloading from a node with IP: ').concat(nodeIP);
    label.hidden = false;
    //Downloading the file MOET HIER KOMEN

}

//Admin functions:
async function allNodes() {

    const tableRef = document.getElementById('nodeTable').getElementsByTagName('tbody')[0];

    //Retreiving all the node information from the naming server
    const nodes = await fetch('http://localhost:8080/admin/nodes')
        .then(nodes => {
            if (nodes.status == 204) {
                throw new Error('There are no nodes in the network')
            } else if (!nodes.ok) {
                throw new Error('Something is wrong with the connection!')
            }
            return nodes.json();
        }).catch(error => {
            console.error('Something went wrong:', error);
            window.alert(error);
        });

    let new_tbody = document.createElement('tbody');

    //Filling the table
    for (let id in nodes){
        // Insert a row in the table at row index 0
        let newRow = new_tbody.insertRow(new_tbody.rows.length);

        // Insert a cell in the row at index 0
        let newCell = newRow.insertCell(0);
        let newCell2 = newRow.insertCell(1);

        // Append a text node to the cell
        let newText = document.createTextNode(id);
        let newText2 = document.createTextNode(nodes[id]);
        newCell.appendChild(newText);
        newCell2.appendChild(newText2);
    }
    tableRef.parentNode.replaceChild(new_tbody, tableRef);


}