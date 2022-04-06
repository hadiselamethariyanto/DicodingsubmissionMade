package com.bwx.core.utils

import com.bwx.core.data.source.local.entity.CastEntity
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.model.Movie
import java.util.ArrayList

object DataDummy {
    fun generateDummyMovies(): List<Movie> {
        val movies = ArrayList<Movie>()

        movies.add(
            Movie(
                1,
                "The Suicide Squad",
                "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
                "2021-07-28",
                "/iCi4c4FvVdbaU1t8poH1gvzT6xM.jpg",
                "/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
                8.1,
                2548,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                2,
                "Jungle Cruise",
                "Dr. Lily Houghton enlists the aid of wisecracking skipper Frank Wolff to take her down the Amazon in his dilapidated boat. Together, they search for an ancient tree that holds the power to heal – a discovery that will change the future of medicine.",
                "2021-07-28",
                "/9dKCd55IuTT5QRs989m9Qlb7d2B.jpg",
                "/7WJjFviFBffEJvkAms4uWwbcVUk.jpg",
                7.9,
                1869,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                3,
                "Free Guy",
                "A bank teller called Guy realizes he is a background character in an open world video game called Free City that will soon go offline.",
                "2021-08-11",
                "/acCS12FVUQ7blkC8qEbuXbsWEs2.jpg",
                "/j28p5VwI5ieZnNwfeuZ5Ve3mPsn.jpg",
                7.9,
                226,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                4,
                "Black Widow",
                "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                "2021-07-07",
                "/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg",
                "/dq18nCTTLpy9PmtzZI6Y2yAgdw5.jpg",
                7.8,
                4325,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                5,
                "Infinite",
                "Evan McCauley has skills he never learned and memories of places he has never visited. Self-medicated and on the brink of a mental breakdown, a secret group that call themselves “Infinites” come to his rescue, revealing that his memories are real.",
                "2021-06-10",
                "/niw2AKHz6XmwiRMLWaoyAOAti0G.jpg",
                "/wjQXZTlFM3PVEUmKf1sUajjygqT.jpg",
                7.4,
                752,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                6,
                "Space Jam: A New Legacy",
                "When LeBron and his young son Dom are trapped in a digital space by a rogue A.I., LeBron must get them home safe by leading Bugs, Lola Bunny and the whole gang of notoriously undisciplined Looney Tunes to victory over the A.I.'s digitized champions on the court. It's Tunes versus Goons in the highest-stakes challenge of his life.",
                "2021-07-08",
                "/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg",
                "/8s4h9friP6Ci3adRGahHARVd76E.jpg",
                7.5,
                1812,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                7,
                "Le Dernier Mercenaire",
                "A mysterious former secret service agent must urgently return to France when his estranged son  is falsely accused of arms and drug trafficking by the government, following a blunder by an overzealous bureaucrat and a mafia operation.",
                "2021-07-30",
                "/ttpKJ7XQxDZV252KNEHXtykYT41.jpg",
                "/rUoGZuscSG4fQP3I56ndadu2A8E.jpg",
                7.1,
                257,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                8,
                "F9",
                "Dominic Toretto and his crew battle the most skilled assassin and high-performance driver they've ever encountered: his forsaken brother.",
                "2021-05-19",
                "/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg",
                "/xXHZeb1yhJvnSHPzZDqee0zfMb6.jpg",
                7.6,
                2889,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                9,
                "The Boss Baby: Family Business",
                "The Templeton brothers — Tim and his Boss Baby little bro Ted — have become adults and drifted away from each other. But a new boss baby with a cutting-edge approach and a can-do attitude is about to bring them together again … and inspire a new family business.",
                "2021-07-01",
                "/kv2Qk9MKFFQo4WQPaYta599HkJP.jpg",
                "/gX5UrH1TLVVBwI7WxplW43BD6Z1.jpg",
                7.8,
                1196,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                10,
                "Luca",
                "Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water’s surface.",
                "2021-06-17",
                "/jTswp6KyDYKtvC52GbHagrZbGvD.jpg",
                "/620hnMVLu6RSZW6a5rwO8gqpt0t.jpg",
                8.1,
                3975,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                11,
                "The Tomorrow War",
                "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school teacher and family man Dan Forester. Determined to save the world for his young daughter, Dan teams up with a brilliant scientist and his estranged father in a desperate quest to rewrite the fate of the planet.",
                "2021-06-30",
                "/34nDCQZwaEvsy4CFO5hkGRFDCVU.jpg",
                "/yizL4cEKsVvl17Wc1mGEIrQtM2F.jpg",
                8.2,
                3496,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                12,
                "Vivo",
                "A music-loving kinkajou named Vivo embarks on the journey of a lifetime to fulfill his destiny and deliver a love song for an old friend.",
                "2021-07-30",
                "/eRLlrhbdYE7XN6VtcZKy6o2BsOw.jpg",
                "/2BftdeCkD7uf68KUxaKRBxtsmZZ.jpg",
                7.8,
                210,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                13,
                "The Kissing Booth 3",
                "It’s the summer before Elle heads to college, and she has a secret decision to make. Elle has been accepted into Harvard, where boyfriend Noah is matriculating, and also Berkeley, where her BFF Lee is headed and has to decide if she should stay or not.",
                "2021-08-11",
                "/c7xcqnMDVQ5v1hJBm3AZ5YikNe6.jpg",
                "/7BkkPaXnYo82T2pLSJP7GRyhD2S.jpg",
                7.7,
                772,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                14,
                "Bartkowiak",
                "After his brother dies in a car crash, a disgraced MMA fighter takes over the family nightclub — and soon learns his sibling's death wasn’t an accident.",
                "/kOVko9u2CSwpU8zGj14Pzei6Eco.jpg",
                "/6tPOZmNQ1tbzlhcMmyhYN1a1dEh.jpg",
                "2021-07-28",
                6.2,
                76,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                15,
                "Jolt",
                "Lindy is an acid-tongued woman with rage issues who controls her temper by shocking herself with an electrode vest. One day she makes a connection with Justin, who gives her a glimmer of hope for a shock-free future, but when he’s murdered she launches herself on a revenge-fueled rampage in pursuit of his killer.",
                "2021-07-15",
                "/gYZAHan5CHPFXORpQMvOjCTug4E.jpg",
                "/wPjtacig0kIkVcTQmXoNt6jbMwo.jpg",
                6.8,
                521,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                16,
                "Don't Breathe 2",
                "The Blind Man has been hiding out for several years in an isolated cabin and has taken in and raised a young girl orphaned from a devastating house fire. Their quiet life together is shattered when a group of criminals kidnap the girl, forcing the Blind Man to leave his safe haven to save her.",
                "2021-08-12",
                "/aOu6PJVO9RyGAzdUwG6fupu0gpz.jpg",
                "/hB8ypGAAq1YiyyTdCSQeFoXHPXW.jpg",
                7.5,
                43,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                17,
                "The Forever Purge",
                "All the rules are broken as a sect of lawless marauders decides that the annual Purge does not stop at daybreak and instead should never end as they chase a group of immigrants who they want to punish because of their harsh historical past.",
                "2021-06-30",
                "/lB068qa6bQ0QKYKyC2xnYGvYjl7.jpg",
                "/tehpKMsls621GT9WUQie2Ft6LmP.jpg",
                7.6,
                993,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                18,
                "Narco Sub",
                "A man will become a criminal to save his family.  Director: Shawn Welling  Writer: Derek H. Potts  Stars: Tom Vera, Tom Sizemore, Lee Majors |",
                "2021-07-22",
                "/7p0O4mKYLIhU2E5Zcq9Z3vOZ4e9.jpg",
                "/vlM3uIetOwIXiBZeLhnzmI5ZoqJ.jpg",
                1.0,
                1,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                19,
                "Cruella",
                "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
                "2021-05-26",
                "/wToO8opxkGwKgSfJ1JK8tGvkG6U.jpg",
                "/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
                8.4,
                4805,
                false,
                ""
            )
        )

        movies.add(
            Movie(
                20,
                "Wrath of Man",
                "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
                "2021-04-22",
                "/M7SUK85sKjaStg4TKhlAVyGlz3.jpg",
                "/77tui163estZrQ78NBggqDB4n2C.jpg",
                7.9,
                1915,
                false,
                ""
            )
        )

        return movies
    }

    fun generateDetailMovie(): MovieEntity {

        return MovieEntity(
            1,
            "The Suicide Squad",
            "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
            "2021-07-28",
            "/iCi4c4FvVdbaU1t8poH1gvzT6xM.jpg",
            "/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
            8.1,
            2548,
            false,
            "",
            1,
            1
        )

    }



}