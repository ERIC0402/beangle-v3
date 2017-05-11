/**
 * SimpleTab.js base on jQuery
 * @author harry
 * @url http://harryxu.cn
 */

function SimpleTab() {
    this.btns = [];
    this.contents = [];
    this.selectedIndex = null;
}

/**
 * Construct a SimpleTab instance.
 *
 * @param btnSelector The buttons selector for tab selection.
 * @param contentSelector The contents selector for tab content.
 * @param selectedBtnClass The class for selected button. [optional].
 * @param viewIndex The initial selected index, default is 0. [optional]
 * @param eventType The event on button for performance tab selection, 
 *                  default is 'click'. [optional]
 *
 * @return the SimpleTab instance.
 */
SimpleTab.construct = function(btnSelector, contentSelector,
                               selectedBtnClass,viewIndex, eventType) {
    var ins = new SimpleTab();
    ins.init(btnSelector, contentSelector, selectedBtnClass,
             viewIndex, eventType);
    return ins;
}

SimpleTab.prototype = {
    
    init:function(btnSelector, contentSelector, 
                  selectedBtnClass, viewIndex, eventType) {
        eventType = eventType || 'click';
        var self = this;
        this.btns = $(btnSelector).map(
            function(index, element) {
                $(this).bind(eventType, function(){
                    self.select(index);
                    return false;
                });
                return $(this);
            }
        ).get();
        
        this.length = this.btns.length;

        this.contents = $(contentSelector).hide().map(
                            function(){ return $(this); }
                        ).get();

        if(selectedBtnClass)
            this.selectedBtnClass = selectedBtnClass;
        
        viewIndex = viewIndex || 0;
            this.select(viewIndex);
    },

    select: function(index) {
        if(isNaN(index) || index<0 || index>=this.length ||
           index==this.selectedIndex)
            return;

        if(this.selectedIndex != null) {
           if(this.selectedBtnClass)
               this.getSelectedBtn().removeClass(this.selectedBtnClass);
           this.getSelectedContent().hide();
        }
        
        this.selectedIndex = index;

        if(this.selectedBtnClass)
            this.getSelectedBtn().addClass(this.selectedBtnClass);
        this.getSelectedContent().show();
    },

    getSelectedBtn: function() {
        return this.btns[this.selectedIndex];
    },

    getSelectedContent: function() {
        return this.contents[this.selectedIndex];
    }
}
