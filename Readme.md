# Proportional Representation Voting

## Project Specifection

Your software system should allow elections to take place in the Munster constituencies with the candidates as laid out in the 2002 election. Each constituency has a number of election candidates associated with it where each candidate can belong to one constituency and one political party. Each constituency has an allocated number of positions (seats) that may be filled by the elected candidates. Your electoral process should allocate seats to the available candidates until all seats in the constituency have been filled. Candidates are elected according to the system of proportional representation as described at the URL provided above.

The combined result of all these constituencies generates the overall election result determining what political party obtain the most number of seats and hence win the election (At least for Munster).

Your system should allow an election to be set up by reading the file described below, taking in the votes for each candidate (first preference, second preference, etc), determine who has been elected and transfer surplus votes to candidates that have not been elected. This process should continue until the number of candidates elected in a constituency equals the number of seats available in the constituency.

Your system should generate statistics about the election such as the number of vote’s cast, the number of first preferences, second preferences etc, for each candidate and for each party, both at a constituency and at a Munster level. Information about candidates elected and eliminated on the 1st count, 2nd count etc should also be available.

Your implementation should include a substantial test file generation module (e.g. incorporating random casting of votes) that demonstrates that your electronic voting system works correctly. Marks will be allocated for this test module. It should be possible to run your electoral system using the files generated from the test system. It should also be possible to run your electoral system by obtaining data from other students files by sticking to the common data format below.

Sample votes.dat format:
```xml
<ELECTION>
<CONS>
1 Clare
2 Cork East
3 Cork North Central
4 Cork North West
5 Cork South Central
6 Cork South West
7 Kerry North
8 Kerry South
9 Limerick East
10 Limerick West
11 Tipperary North
12 Tipperary South
13 Waterford
</CONS>

<ELEC>
<ENAME>
Clare
</ENAME>
<NUMOFSEATS>
4
</NUMOFSEATS>
<CAND>
1 Breen, James, ind
2 Breen, Pat , FG
3 Carey, Donal, FG
4 Corley, Michael, Lab
5 Daly, Brendan, FF
6 De Valera, Síle, FF
7 Killeen, Tony, FF
8 Meaney, Brian, GP
9 Taylor-Quinn, FG
10 Whelan, Derek J, CSP
</CAND>
<BALLOTS>
3, 4, 5, 7, 10, 12, 1, 6, 8, 2, 9, 11
3, 4, 6, 7, 10, 11, 1, 5, 8, 2, 9, 12
1, 4, 5, 7, 10, 11, 3, 6, 8, 2, 9, 12
3, 4, 5, 10, 7, 11, 1, 6, 8, 2, 9, 12
</BALLOTS>
</ELEC>

<ELEC>
<ENAME>
Cork East
</ENAME>
<NUMOFSEATS>
4
</NUMOFSEATS>
<CAND>
… more names here
</CAND>
<BALLOTS>
3, 4, 5, 7, 10, 6, 8, 2, 9, 1
10, 3, 4, 6, 7, 1, 5, 9, 2, 8
1, 4, 0, 0, 3, 0, 0, 2, 0, 0
3, 4, 5, 7, 1, 6, 8, 2, 0, 0
</BALLOTS>

</ELEC>

Lots more stuff

</ELECTION>
```
votes are in order of candidates

Each line in each ballot represents a single voter

N.B. There can be hundreds of lines (ballots) all of equal length (zeros will fill the gaps were a preference has not been written)

e.g. vote does not transfer after 4th preference for ballot 3

 

Number of voters per constituency is 1000 so turnout must be less than this and a % turnout displayed.

| | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 
--- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- 
|Ballot 1 | 3 | 4 | 5 | 7 | 10 | 6 | 8 | 2 | 9 | 1 
|Ballot 2 |
|Ballot 3 | 1 | 4 | 0 | 0 | 3 | 0 | 0 | 2 | 0 | 0 
|Ballot 4 |

Or (How Ballot 1 might look on paper)

 

Name | Preference
--- | --- 
1 Breen | 3
2 Breen | 4
3 Carey | 5
4 Corley | 7
5 Daly | 10
6 De Valera | 6
7 Killeen | 8
8 Meaney | 2
9 Taylor-Quinn | 9
10 Whelan | 1


## Solution Assumptions

1. Parties are registered in advance and have been hardcoded.
1. Surplus votes are redistributed randomly.
1. Candidates are created at random.
1. Statistics are not shown for random votes.
1. A Candidate's number of preference votes are only displayed for first preference votes.

## Acknowledgements

Candiate names were generated using [listofrandomnames.com](http://listofrandomnames.com)

