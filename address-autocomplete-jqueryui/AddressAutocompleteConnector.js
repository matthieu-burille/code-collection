class AddressAutocompleteConnector {

    elementSelectors = {
        searchInput: "#address",
        addressInput: "#address",
        zipInput: "#zipCode",
        cityInput: "#city",
        countrySelect: "#country"
    }

    options = {
        searchUri: "https://api-adresse.data.gouv.fr/search",
        autocomplete: 1, //Paramètre d'autocomplétion de la BAN
        limit: 15,
        minChar: 3,
        submitDelay: 500
    }

    _searchInput;
    _addressInput;
    _zipInput;
    _cityInput;
    _countrySelect;

    _fetchTrigger = 0;
    
    initialize(elementSelectors, options) {
        if (elementSelectors) {
            this.elementSelectors = elementSelectors;
        }
        if (options) {
            this.options = options;
        }
        this._searchInput = $(this.elementSelectors.searchInput);
        this._addressInput = $(this.elementSelectors.addressInput);
        this._zipInput = $(this.elementSelectors.zipInput);
        this._cityInput = $(this.elementSelectors.cityInput);
        
        this._countrySelect = $(this.elementSelectors.countrySelect);
        this._countrySelect.selectmenu().addClass("addressInput");
        
        // Initialize la widget autocomplete de jquery-ui :
        // source : appelle la fonction renvoyant une liste
        // response : traite la liste (par exemple pour formater les données de la liste)
        // renderItem : renvoie le html de chaque item pour chaque élément de la liste formatée dans response 
        // select : appelle le traitement à faire quand on clique sur un item de la liste html
        this._searchInput.autocomplete({
			source : this.onSearch,
			select : this.onSelectAddress,
			minLength : this.options.minChar
		}) .autocomplete( "instance" )._renderItem = this.renderItem;
    }

    onSearch = (request, response) => {
		// Si le pays n'est pas la France on n'appelle pas l'API BAN (vide -> on l'appelle)
		let country = this._countrySelect.val();
		if(!!country && country.toUpperCase() != "FRANCE"){
			return false;
		}
			
        // On annule une éventuelle précédente requête en attente
        clearTimeout(this._fetchTrigger);

        let address = request.term;
        this._fetchTrigger = setTimeout(this.callBanApi(address, response), this.options.submitDelay);
    }
    
    onSelectAddress = (event, ui) => {
        this._addressInput.val(ui.item.address);
        this._zipInput.val(ui.item.zipCode);
        this._cityInput.val(ui.item.city);
        this._countrySelect.val("france");
        this._countrySelect.selectmenu("refresh");
		// Arrête l'event pour que le comportement par défaut de la widget ne pas remplisse pas le champ de saisie
		return false;
    }
    
    renderItem = (ul, item) => {
		let addressLabel = item.address + " " + item.zipCode + " <strong>" + item.city + "</strong>";
		let div = $('<div>' + addressLabel + '</div>');
		return $('<li></li>').append(div).appendTo(ul);
	}

	// response : retour utilisée par la widget autocomplete de jquery-ui
	callBanApi = (address, response) => {
        $.get(this.options.searchUri, {
            q: address,
            limit: this.options.limit,
            autocomplete: this.options.autocomplete
        }, (data, status, xhr) => {
			let list=[];
			$.each(data.features, function(i, obj) {
				list.push({
					address : obj.properties.name,
					zipCode : obj.properties.postcode,
					city : obj.properties.city
				});
	        });
	        response(list);
	    }, 'json');
    }
}