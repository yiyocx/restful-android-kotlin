package com.taskworld.android.restfulandroidkotlin.network.action

import com.taskworld.android.restfulandroidkotlin.network.BaseAction
import com.taskworld.android.restfulandroidkotlin.network.BaseReaction
import com.taskworld.android.restfulandroidkotlin.network.ActionExecutor
import com.octo.android.robospice.SpiceManager
import de.greenrobot.event.EventBus


/**
 * Created by Johnny Dew on 11/12/2014 AD.
 */

class GetMovieFeedAction() : BaseAction() {

}

class GetMovieFeedReaction() : BaseReaction() {

    var isCached = false
    //TODO list of movie variable goes here

}

class GetMovieFeedActionExecutor(action: GetMovieFeedAction, spiceManager: SpiceManager) : ActionExecutor(action, spiceManager) {

    override fun execute() {

        //TODO implement get movie feed action here

        //TODO Get cached feed and send back to caller via EventBus
        var reaction = GetMovieFeedReaction()
        reaction.isCached = true
        EventBus.getDefault().post(reaction)

        //Get API feed : Robospice + Retrofit
    }

}