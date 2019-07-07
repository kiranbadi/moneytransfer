/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Kiran
 * Created: Jul 6, 2019
 */

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCCOUNT_NUMBER` int(11) NOT NULL,
  `CUSTOMER_NUMBER` int(11) NOT NULL,
  `TRANSACTION_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `AMOUNT` decimal(13,4) NOT NULL,
  `ACCOUNTTYPE` varchar(45) NOT NULL,
  `TRANSACTIONID` mediumint(11) NOT NULL,
  `TRANSACTION_CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TRANSACTION_UPDATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TRANSACTIONTYPE` varchar(45) NOT NULL
 )

