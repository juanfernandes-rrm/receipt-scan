# ReceiptScan

ReceiptScan é um serviço do **Nota Social** responsável pela extração de informações de Notas Fiscais de Consumidor Eletrônicas (NFC-e) utilizando técnicas de web scraping. Este serviço foi desenvolvido em Java com o objetivo de facilitar a extração, organização e análise de dados contidos nos documentos fiscais.

Os dados gerados pelo ReceiptScan são armazenados em um banco de dados dedicado e compartilhados com os serviços **Catalog** e **Register** por meio de filas, garantindo a integração eficiente e a consistência das informações no ecossistema do Nota Social.
Funcionalidades principais

  - Extração de detalhes de produtos, valores e informações sobre estabelecimentos comerciais a partir de NFC-e.
  - Organização e armazenamento de dados em um formato estruturado.
  - Disponibilização dos dados extraídos para outros serviços do Nota Social através de filas de mensagens.
