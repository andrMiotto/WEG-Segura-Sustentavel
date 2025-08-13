# Front-End - Integração com Node-RED

## 👥 Integrantes
<p>
  <a href="https://github.com/andrMiotto">
    <img src="https://github.com/andrMiotto.png" width="100" alt="André Luis Miotto Pereira"/>
  </a>
  <a href="https://github.com/KaelLuih">
    <img src="https://github.com/KaelLuih.png" width="100" alt="Kael Luih de Araujo"/>
  </a>
  <a href="https://github.com/Jose7764">
    <img src="https://github.com/Jose7764.png" width="100" alt="José Azarías Pérez Torres"/>
  </a>
</p>

---

## 🛠 Tecnologias Utilizadas
- **HTML5** - Estrutura das páginas
- **CSS3** - Estilização e responsividade
- **Node-RED** - Orquestração do fluxo de dados entre front e back
- **MQTT/HTTP** - Protocolos para envio e recebimento de dados

---

## 📜 Trajetória do Desenvolvimento
1. Iniciamos o projeto definindo o **fluxo de dados** que precisávamos entre sensores/dispositivos e a interface web.
2. Conversamos com outros grupos para entender como seria feita a integração com o **Node-RED** e padronizar formatos de envio/recebimento.
3. Criamos a estrutura base do front-end, priorizando simplicidade e velocidade na exibição dos dados.
4. Enfrentamos dificuldades na **integração com o back-end** por falta de experiência com essa comunicação, especialmente na ponte entre Node-RED e a aplicação Java.
5. A solução foi de integrar diretamente com o Back-end, para ter uma comunicação mais rápida e já formatada

---

## 🔄 Estrutura Lógica de Comunicação

O fluxo de dados entre front-end e back-end funciona da seguinte forma:

1. **Coleta de Dados**
   - Os sensores/dispositivos enviam dados para o **Node-RED** via MQTT ou HTTP.
   
2. **Processamento**
   - O Node-RED processa e padroniza os dados recebidos.
   
3. **Envio ao Front-End**
   - O Node-RED disponibiliza os dados através de um endpoint HTTP ou via WebSocket.
   
4. **Consumo no Front-End**
   - O front-end consome os dados usando **fetch()** ou WebSocket, atualizando a interface em tempo real.
   
5. **Ações do Usuário**
   - Quando o usuário realiza uma ação no site (ex: enviar comando para um dispositivo), o front envia o comando para o Node-RED, que repassa ao sistema de controle ou ao Java.

**Fluxo Resumido:**

