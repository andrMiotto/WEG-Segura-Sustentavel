let contSalasOcupadas = 0;
let contSalasVazias = 0;
let totalPessoas = 0;
let contDivs = 1;
let index = 1;
// <summary>
//  variaveis declaradas aqui em cima por persistência
// </summary>

fetch('/api/salas').then(res => res.json()).then(salas => {
    salas.forEach((sala, i) => {
        let salaIndex = "sala" + (i+1);

        document.getElementById(salaIndex).innerHTML = sala.numero;
    })
})

fetch('/api/pessoas').then(res => res.json()).then(pessoas => {
    pessoas.forEach((pessoa, i) => {
        let pessoaIndex = "pessoas" + (i+1);

        document.getElementById(pessoaIndex).innerHTML = pessoa.total_pessoas;

        if(pessoa.total_pessoas > 0){
            contSalasOcupadas++;
        }else{
            contSalasVazias++;
        }

        document.getElementById("salaOcupada").innerHTML = contSalasOcupadas;
        document.getElementById("salaVazia").innerHTML = contSalasVazias;

        totalPessoas += pessoa.total_pessoas;

        document.getElementById("totalAlunos").innerHTML = totalPessoas;
    })
})

fetch('/api/emergencias').then(res => res.json()).then(emergencias => {
    emergencias.forEach((emergencia, i) => {
        if(contDivs > 1){
            const divPai = document.getElementById('card-emergencias');

            const div = document.createElement('div');
            div.classList.add('d-flex', 'align-items-center', 'mb-3');
            div.id = 'emergencia' + index;
            divPai.appendChild(div);

            const roundDiv = document.createElement('div');
            roundDiv.id = 'circuloVermelho' + index;
            roundDiv.classList.add('bg-danger', 'rounded-circle', 'me-3');
            roundDiv.style.width = '10px';
            roundDiv.style.height = '10px';
            div.appendChild(roundDiv);

            const contentWrapper = document.createElement('div');
            div.appendChild(contentWrapper);

            const fimInicio = document.createElement('div');
            fimInicio.classList.add('fimInicio' + index);
            contentWrapper.appendChild(fimInicio);

            const inicioDiv = document.createElement('small');
            inicioDiv.id = 'inicioEmergencia' + index;
            inicioDiv.classList.add('text-muted');
            fimInicio.appendChild(inicioDiv);

            fimInicio.appendChild(document.createTextNode(' - '));

            const fimDiv = document.createElement('small');
            fimDiv.id = 'fimEmergencia' + index;
            fimDiv.classList.add('text-muted');
            fimInicio.appendChild(fimDiv);

            const tituloDiv = document.createElement('p');
            tituloDiv.id = 'tituloEmergencia' + index;
            tituloDiv.classList.add('mb-0', 'fs-5', 'fw-bold');
            contentWrapper.appendChild(tituloDiv);

            const descDiv = document.createElement('p');
            descDiv.id = 'descricaoEmergencia' + index;
            descDiv.classList.add('mb-0');
            contentWrapper.appendChild(descDiv);
        }

        document.getElementById("inicioEmergencia" + index).innerHTML = emergencia.inicio;

        if(emergencia.fim == null){
            document.getElementById("fimEmergencia" + index).innerHTML = "Fim não definido";
        }else{
            document.getElementById("fimEmergencia" + index).innerHTML = emergencia.fim;
        }
        
        document.getElementById("tituloEmergencia" + index).innerHTML = emergencia.titulo;
        document.getElementById("descricaoEmergencia" + index).innerHTML = emergencia.descricao;

        index++;
        contDivs++;
    })
})