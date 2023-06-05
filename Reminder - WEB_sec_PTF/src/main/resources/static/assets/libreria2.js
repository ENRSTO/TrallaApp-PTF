const vm = new Vue({
  el: '#app',
  data: {
    name: 'Vue',  
    randomNumber: 0,
    mittent : null,
    mail: null,
    host: null,
    port: null,
    user: null,
    googleKey: null,
    state : null,
    htmlTest :` <p style="color: blue; font-size: 2em;" class="my-class">
               codice html
              </p>`,
    event: 'input',
    errorMsg: '',
    errorMsgMail: '',
    errorMsgHost:'',
    errorMsgPort:'',
    baseStyles: {
      fontSize: "1em",
      fontWeight: 100,
      color: "red"
    }
  },

  methods: {    

    saveImpostazioni(){

      const field = document.getElementById('mitt').value;
      const key = document.getElementById('utente').value;
      const Pmail = document.getElementById('mail').value;
      const Phost = document.getElementById('host').value;
      const PPort = document.getElementById('porta').value;
      const PGoogleKey = document.getElementById('googleKey').value;
      
      if (this.mittent == null) {
        this.mittent = field
      }          
      if (this.mail == null) {
        this.mail = Pmail
      }          
      if (this.host == null) {
        this.host = Phost
      }     
      if (this.port == null) {
        this.port = PPort
      }   
      if (this.port == null) {
        this.port = PPort
      }    
      if (this.googleKey == null) {
        this.googleKey = PGoogleKey
      }     

      let data = {
                utente : key,
                mittente: this.mittent,
                mail: this.mail,
                host: this.host,
                port: this.port,
                googleKey: this.googleKey,
                user : this.user   // campo per reperimento user loggato
      };
    
    

          fetch('/saveImpo', {
              method: 'POST', // or 'PUT'
              headers: new Headers({   'Accept': 'application/json',
                'Content-Type': 'application/json'
                }),
              body: JSON.stringify(data)
               // body: data, 
            })
            .then(async res => res.text)  
            
    }, // fine saveImpostazioni 

    id: function (buttonId) {
      console.log(`Pulsante ${buttonId}`);
    },

    GetImpostazioni (){

    },
 
// metodi di validazione 
    validate(event) {
      const inputValue = event.target.value;
      const pattern = /^[a-zA-Z ]+$/;
      if (pattern.test(inputValue) === false) {
        this.errorMsg = 'campo Mittente non valido';        
      } else {
        this.errorMsg = '';
      }
      this.mittent = inputValue
    },
    validateMail(event) {
      
      const inputValue = event.target.value;
      const pattern = /^[a-zA-Z@. ]+$/;
      if (pattern.test(inputValue) === false) {
        this.errorMsgMail = 'campo Mail non valido';
      } else {
        this.errorMsgMail = '';
      }
      this.mail = inputValue      
    },
    validateHost(event){
      
      const inputValue = event.target.value;
      const pattern = /^[0-9.; ]+$/;
      if (pattern.test(inputValue) === false) {
        this.errorMsgHost = 'campo Host non valido';
      } else {
        this.errorMsgHost = '';
      }
      this.host = inputValue
    },    
    validatePort(event){
      
      const inputValue = event.target.value;
      const pattern = /^[0-9. ]+$/;
      if (pattern.test(inputValue) === false) {
        this.errorMsgPort = 'campo Port non valido';        
      } else {
        this.errorMsgPort = '';
      }
      this.port = inputValue
    }


  }


});