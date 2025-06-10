// GooglePayButton.js - JavaScript interface for the Cordova plugin

class GooglePayButton {
    constructor() {
        this.isReady = false;
        this.callbacks = {};
        
        // Wait for Cordova to be ready
        document.addEventListener('deviceready', () => {
            this.isReady = true;
        }, false);
    }
    
    /**
     * Create the Google Pay button with specified options
     * @param {Object} options - Button configuration options
     * @param {string} options.theme - 'light' or 'dark'
     * @param {string} options.type - 'buy', 'checkout', 'pay', etc.
     * @param {number} options.cornerRadius - Corner radius in dp
     * @param {string} options.allowedPaymentMethods - JSON string of allowed payment methods
     * @param {Function} callback - Callback function for button clicks
     */
    createButton(options, callback) {
        return new Promise((resolve, reject) => {
            if (!this.isReady) {
                reject(new Error('Cordova not ready'));
                return;
            }
            
            // Store the click callback
            if (callback) {
                this.callbacks.onClick = callback;
            }
            
            cordova.exec(
                (result) => {
                    if (result === 'payButtonClicked' && this.callbacks.onClick) {
                        this.callbacks.onClick();
                    } else {
                        resolve(result);
                    }
                },
                (error) => reject(error),
                'GooglePayButtonPlugin',
                'createPayButton',
                [options]
            );
        });
    }
    
    /**
     * Show the Google Pay button at specified position
     * @param {Object} position - Button position and size
     * @param {number} position.x - X coordinate in dp
     * @param {number} position.y - Y coordinate in dp
     * @param {number} position.width - Width in dp
     * @param {number} position.height - Height in dp
     */
    showButton(position) {
        return new Promise((resolve, reject) => {
            if (!this.isReady) {
                reject(new Error('Cordova not ready'));
                return;
            }
            
            cordova.exec(
                resolve,
                reject,
                'GooglePayButtonPlugin',
                'showPayButton',
                [position]
            );
        });
    }
    
    /**
     * Hide the Google Pay button
     */
    hideButton() {
        return new Promise((resolve, reject) => {
            if (!this.isReady) {
                reject(new Error('Cordova not ready'));
                return;
            }
            
            cordova.exec(
                resolve,
                reject,
                'GooglePayButtonPlugin',
                'hidePayButton',
                []
            );
        });
    }
    
    /**
     * Update button options
     * @param {Object} options - New button options
     */
    updateButton(options) {
        return new Promise((resolve, reject) => {
            if (!this.isReady) {
                reject(new Error('Cordova not ready'));
                return;
            }
            
            cordova.exec(
                resolve,
                reject,
                'GooglePayButtonPlugin',
                'updatePayButton',
                [options]
            );
        });
    }
}

// Export for use in React/ES6 modules
export default new GooglePayButton();