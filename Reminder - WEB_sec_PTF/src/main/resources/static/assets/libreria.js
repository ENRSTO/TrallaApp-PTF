const formcomponent = {



    data(){
       return {
           state : this.ref,
           mittent : null,
           mail : null,
           host : null,
           port : null,
           googleKey : null,
           errors :[],
           data1 :null
       }
    },
    methods: {
        saveImpostazioni(){    

            let data = {
                      mittente: this.mittent,
                      mail: this.mail,
                      host: this.host,
                      port: this.port,
                      googleKey: this.googleKey
                    };
          //datas = JSON.stringify(data);
          
    
                fetch('/saveImpo', {
                    method: 'POST', // or 'PUT'
                    headers: new Headers({   'Accept': 'application/json',
                      'Content-Type': 'application/json'
                      }),
                    body: JSON.stringify(data),
                     // body: data, 
                  })
                  .then(res => res.json())
         //.then((response) => response.json())/*
                  .then((data1) => {
                      console.log('Success:', data1);
                  })
                  .catch(alert);
            
          }
    }

    
}
const myForm = Vue.createApp(formcomponent).mount('#myform')