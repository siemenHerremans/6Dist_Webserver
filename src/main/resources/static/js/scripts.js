//General functions:
async function downloadFile(fileName) {

    //Resolving the correct IP adress where the file should be located
    const nodeIP = await fetch('http://localhost:8080/user/location?filename='.concat(fileName))
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

    //Check if file exists
    await fetch('http://localhost:8080/user/exists?ip='.concat(nodeIP).concat('&filename=').concat(fileName))
        .then(response => {
            if (response.status == 204) {
                throw new Error('The file is not found on the node!');
            } else if (!response.ok) {
                console.log(response.status);
                throw new Error('Something is wrong with the connection!')
            }

            const url = 'http://localhost:8080/user/getfile/'.concat(nodeIP).concat('/').concat(fileName);
            console.log(url);
            let link = document.getElementById("downlink");
            link.setAttribute("href", url);
            link.click();


        }).catch(error => {
            console.error('Something went wrong:', error);
            window.alert(error);
        });
}

//User clicked functions

function downloadClicked() {
    const input = document.getElementById('filenameField').value;
    downloadFile(input);
}


//Admin clicked functions:
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

    const new_tbody = document.createElement('tbody');

    //Filling the table
    for (let id in nodes) {
        // Insert a row in the table at row index 0
        let newRow = new_tbody.insertRow(new_tbody.rows.length);
        //Make rows clickable
        newRow.setAttribute('onclick', 'openNodeWindow(\"'.concat(nodes[id]).concat('\")'));
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

function openNodeWindow(nodeIP) {
    //console.log(nodeIP);
    window.open('http://localhost:8080/admin/'.concat(nodeIP), '_blank');
}

async function loadNodeAdmin() {

    let url = window.location.href.toString().split("admin/");
    url = url[1];

    //Retrieve filenames
    const fileLists = await fetch('http://localhost:8080/admin/allfiles?ip='.concat(url))
        .then(fileLists => {
            if (fileLists.status == 204) {
                throw new Error('There no files on the node')
            } else if (!fileLists.ok) {
                throw new Error('Something is wrong with the connection!')
            }
            return fileLists.json();
        }).catch(error => {
            console.error('Something went wrong:', error);
            window.alert(error);
        });

    //const tableLocalRef = document.getElementById('ronnyFileTable').getElementsByTagName('tbody')[0];
    //const tableReplRef = document.getElementById('replicatedFileTable').getElementsByTagName('tbody')[0];
    const tableLocalRef = document.getElementById('localBody');
    const tableReplRef = document.getElementById('replicatedBody');
    const tableRef = [tableReplRef, tableLocalRef];

    //Filling the table

    let tableCounter = 0;

    let new_tbody = [];

    fileLists.forEach(fileList => {

        new_tbody[tableCounter] = document.createElement('tbody');

        fileList.forEach(file => {
            // Insert a row in the table at row index 0
            let newRow = new_tbody[tableCounter].insertRow(new_tbody[tableCounter].rows.length);

            newRow.setAttribute('onclick', 'downloadFile(\"'.concat(file.toString()).concat('\")'));

            let newCell = newRow.insertCell(0);
            let newText = document.createTextNode(file.toString());
            newCell.appendChild(newText);

        })
        tableRef[tableCounter].parentNode.replaceChild(new_tbody[tableCounter], tableRef[tableCounter]);
        tableCounter++;
    })
}

async function shutdown() {
    let url = window.location.href.toString().split("admin/");
    const nodeip = url[1];

    await fetch('http://localhost:8080/admin/shutdown?ip='.concat(nodeip));
    alert("Node has been terminated!");

    window.location = 'http://localhost:8080/admin/home';
}
