package com.cscie88cfinal

import scala.util.{ Try }

final case class RawBillsandlaws(
                                  legislation_num: String,
                                  leg_url: String,
                                  leg_congress: String,
                                  leg_title: String,
                                  amends_bill: String,
                                  leg_sponsor: String,
                                  dOffered: String,
                                  dIntroduction: String,
                                  numCospons: String,
                                  dateSub: String,
                                  dateProp: String,
                                  committees: String,
                                  latestAct: String,
                                  laDate: String,
                                  cosponsors: String,
                                  subject: String,
                                  relatedBills: String,
                                  latestSum: String,
                               )

final case class Billsandlaws99to22(
                                     legislationNum: String,
                                     url: String,
                                     congress: String,
                                     title: String,
                                     amendsBill: String,
                                     sponsor: String,
                                     dOffered: String,
                                     dIntroduction: String,
                                     numCospons: String,
                                     dateSub: String,
                                     dateProp: String,
                                     committees: String,
                                     latestAct: String,
                                     laDate: String,
                                     cosponsors: String,
                                     subject: String,
                                     relatedBills: String,
                                     latestSum: String,
                                   ) {

}
