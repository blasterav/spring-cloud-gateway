Feature: Test

  Scenario: pathMatches('/api/activity') && methodIs('get')
    * def sleep = function(seconds){ java.lang.Thread.sleep(seconds*1000) }
    * call sleep 4
    * def response = { "activity": "Cook something together with someone", "type": "cooking", "participants": 2, "price": 0.3, "link": "", "key": "1799120", "accessibility": 0.8 }

  Scenario: pathMatches('/api/signature') && methodIs('get')
    * def responseHeaders = { 'Signature': 'alex1' }
    * def response = { "activity": "Cook something together with someone", "type": "cooking", "participants": 2, "price": 0.3, "link": "", "key": "1799120", "accessibility": 0.8 }
    * print 'created response is: ' + requestHeaders.signature
    * print '>>', requestHeaders['signature']

